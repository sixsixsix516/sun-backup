mysqldump -uroot -p'!@ChaoRen!nstallation!!!'  superman>superman-$(date "+%Y.%m.%d").sql

sudo docker exec -it $DOCKER_ID /bin/bash -c 'cd /packages/detectron && python tools/train.py'
