#create databases
CREATE DATABASE IF NOT EXISTS `wroom`;
CREATE DATABASE IF NOT EXISTS `agent`;
CREATE DATABASE IF NOT EXISTS `search`;

#create root user and grant rights
CREATE USER IF NOT EXISTS 'wroom'@'%' IDENTIFIED WITH mysql_native_password BY 'wroom';
GRANT ALL PRIVILEGES ON wroom.* TO 'wroom'@'%';

CREATE USER IF NOT EXISTS 'agent'@'%' IDENTIFIED WITH mysql_native_password BY 'agent';
GRANT ALL PRIVILEGES ON agent.* TO 'agent'@'%';

CREATE USER IF NOT EXISTS 'search'@'%' IDENTIFIED WITH mysql_native_password BY 'search';
GRANT ALL PRIVILEGES ON search.* TO 'search'@'%';

