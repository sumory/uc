kill -9 `cat /tmp/memcached_uc.pid`
if [ $? -eq 0 ]; then
	echo 'kill success'
else
	echo 'kill error, no process'
fi

echo 'param is : '$1

cd /home/zd/software/memcached/bin/

#kill memecache
if [ $1 = '118' ]; then
	echo 'kill memcache on 191.168.1.118'
	./memcached -d -p 11211 -m 2048 -u root -l 192.168.1.118 -M -P /tmp/memcached_uc.pid
elif [ $1 = 'edusns1' ]; then
	echo 'kill memcache on edusns1'
	./memcached -d -p 11211 -m 2048 -u root -l edusns1 -M -P /tmp/memcached_uc.pid
else
	echo 'wrong params, param should be: 118 or edusns1'
fi



