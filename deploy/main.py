import subprocess
from signal import SIGTERM

from psutil import process_iter

PRIMARY_PORT = 8080
SECONDARY_PORT = 8081
NGINX_CONF_PATH = "/etc/nginx/nginx.conf"

green_port = PRIMARY_PORT


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


def start_green():
    print(f"Checking port {PRIMARY_PORT}")
    for proc in process_iter():
        for conns in proc.net_connections(kind="inet"):
            if conns.laddr.port == PRIMARY_PORT:
                print(
                    f"Process already running on {PRIMARY_PORT}. Attempting to start on {SECONDARY_PORT}"
                )
                green_port = SECONDARY_PORT
    java_proc = start_java_process(green_port)

    for line in java_proc.stdout:
        if "APPLICATION FAILED TO START" in line.decode("utf-8"):
            raise RuntimeError(
                f"Processes already running on ports {PRIMARY_PORT} and {SECONDARY_PORT}"
            )
        elif "Started TexasApplication" in line.decode("utf-8"):
            print("Green application started")
            break


def point_to_green():
    with open(NGINX_CONF_PATH, "r") as f:
        conf = f.read()
    if f"server localhost:{PRIMARY_PORT}" in conf:
        conf.replace(
            f"server localhost:{PRIMARY_PORT}", f"server localhost:{SECONDARY_PORT}"
        )
    else:
        conf.replace(
            f"server localhost:{SECONDARY_PORT}", f"server localhost:{PRIMARY_PORT}"
        )
    with open(NGINX_CONF_PATH, "w") as f:
        f.write(conf)
    subprocess.Popen(["sudo", "nginx", "-s", "reload"]).communicate()
    print("Nginx config updated to point to green application")


def stop_blue():
    for proc in process_iter():
        for conns in proc.net_connections(kind="inet"):
            if conns.laddr.port == green_port:
                proc.send_signal(SIGTERM)
    print("Stopped blue application")


def main():
    start_green()
    point_to_green()
    stop_blue()


if __name__ == "__main__":
    main()
