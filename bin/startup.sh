#!/bin/bash
#启动脚本,请在bin目录下运行
if [ $# != 2 ]
then
    echo "error : the program need two args.1:port,2:ip"
    return
fi

java -Xms1024m -Xmx1024m -cp ../lib/*:../release/com.uucampus.sns.uc.jar com.uucampus.sns.uc.main.Server "$1" "$2" &>> ../log/server.log
