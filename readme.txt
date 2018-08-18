编译工程 mvn compile
测试工程 mvn test
分析类库依赖 mvn dependency:tree --> tree.txt

idea运行
编辑Run/Debug Configurations，在中下部Active Profiles 输入需要连接的环境，如：dev

打包
测试环境 mvn clean package -Dmaven.test.skip=true -Puat
生产环境 mvn clean package -Dmaven.test.skip=true -Pprd


windown运行命令
java -jar target/nma.jar --server.port=10001 (指定端口)

linux行命令
nohup java -jar nma.jar --server.port=10001 > /dev/null 2>&1 &
tailf boot_app_logs/plum/log_debug.log