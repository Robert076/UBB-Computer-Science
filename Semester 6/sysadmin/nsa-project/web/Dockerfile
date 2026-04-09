FROM php:8.0-apache
RUN docker-php-ext-install mysqli pdo pdo_mysql
COPY index.php /var/www/html/index.php
RUN pecl install redis && docker-php-ext-enable redis
