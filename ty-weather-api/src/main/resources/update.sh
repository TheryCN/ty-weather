sudo systemctl stop weather.service
git pull origin master
mvn clean install
sudo systemctl start weather.service