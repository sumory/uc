#!/bin/bash

echo 'param is : '$1

#清空memecache
if [ $1 = '118' ]; then
	echo 'flush 191.168.1.118'
	echo "flush_all" | nc 192.168.1.118 11211
elif [ $1 = 'edusns1' ]; then
	echo 'flush online machine'
	echo "flush_all" | nc edusns1 11211
else
	echo 'wrong params, param should be: 118 or edusns1'
fi

#初始化数据
echo 'init data......'
java -cp ../lib/*:../release/com.uucampus.sns.uc.jar com.uucampus.sns.uc.init.InitData &> ../log/initData.log