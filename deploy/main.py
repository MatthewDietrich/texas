import subprocess
from signal import SIGTERM

from psutil import process_iter

PRIMARY_PORT = 8080
SECONDARY_PORT = 8082
NGINX_CONF_PATH = "/etc/nginx/nginx.conf"

green_port = PRIMARY_PORT
blue_port = SECONDARY_PORT


def start_java_process(port: int) -> subprocess.Popen:
    return subprocess.Popen(
        [
            "/usr/bin/java",
            "-Xmx256m",
            "-jar",
            "/home/ubuntu/texas-0.0.1-SNAPSHOT.war",
            f"--server.port={port}",
        ],
        stdout=subprocess.PIPE,
        stderr=subprocess.PIPE,
    )


def start_green() -> None:
    global green_port
    global blue_port
    print(f"Attempting to start on {PRIMARY_PORT}")
    for proc in process_iter():
        for conns in proc.net_connections(kind="inet"):
            if conns.laddr.port == PRIMARY_PORT:
                print(
                    f"Process already running on {PRIMARY_PORT}. Attempting to start on {SECONDARY_PORT}"
                )
                green_port = SECONDARY_PORT
                blue_port = PRIMARY_PORT
    java_proc = start_java_process(green_port)

    for line in java_proc.stdout:
        if "APPLICATION FAILED TO START" in line.decode("utf-8"):
            raise RuntimeError(
                f"Processes already running on ports {PRIMARY_PORT} and {SECONDARY_PORT}"
            )
        elif "Started TexasApplication" in line.decode("utf-8"):
            print(f"Green application started on port {green_port}")
            break


def point_to_green() -> None:
    global green_port
    global blue_port
    with open(NGINX_CONF_PATH, "r") as f:
        conf = f.read()
    conf = conf.replace(
        f"server localhost:{blue_port}", f"server localhost:{green_port}"
    )
    with open(NGINX_CONF_PATH, "w") as f:
        f.write(conf)
    subprocess.Popen(["sudo", "nginx", "-s", "reload"]).communicate()
    print(f"Nginx config updated to point to port {green_port}")


def stop_blue() -> None:
    global blue_port
    for proc in process_iter():
        for conns in proc.net_connections(kind="inet"):
            if conns.laddr.port == blue_port:
                proc.send_signal(SIGTERM)
    print(f"Stopped application on port {blue_port}")


def main():
    start_green()
    point_to_green()
    stop_blue()


if __name__ == "__main__":
    main()
