编译工程 gradle build

windown运行命令
java -jar target/nma.jar --server.port=10001 (指定端口)

linux行命令
nohup java -jar nma.jar --server.port=10001 > /dev/null 2>&1 &
tailf boot_app_logs/plum/log_debug.log