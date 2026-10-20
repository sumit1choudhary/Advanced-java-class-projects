CREATE DATABASE IF NOT EXISTS shopsphere CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE shopsphere;
CREATE TABLE users(user_id INT AUTO_INCREMENT PRIMARY KEY,name VARCHAR(100) NOT NULL,email VARCHAR(150) UNIQUE NOT NULL,
 password VARCHAR(255) NOT NULL,mobile VARCHAR(20),role ENUM('CUSTOMER','ADMIN') DEFAULT 'CUSTOMER',
 status TINYINT DEFAULT 1,created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP);
CREATE TABLE categories(category_id INT AUTO_INCREMENT PRIMARY KEY,name VARCHAR(100) NOT NULL,description VARCHAR(255),status TINYINT DEFAULT 1);
CREATE TABLE products(product_id INT AUTO_INCREMENT PRIMARY KEY,category_id INT,name VARCHAR(150) NOT NULL,brand VARCHAR(100),
 description TEXT,price DECIMAL(10,2) NOT NULL,discount DECIMAL(5,2) DEFAULT 0,stock INT DEFAULT 0,
 image_url VARCHAR(500),status TINYINT DEFAULT 1,created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 FOREIGN KEY(category_id) REFERENCES categories(category_id));
CREATE TABLE cart(cart_id INT AUTO_INCREMENT PRIMARY KEY,user_id INT,product_id INT,quantity INT NOT NULL,
 UNIQUE KEY uq_cart(user_id,product_id),FOREIGN KEY(user_id) REFERENCES users(user_id) ON DELETE CASCADE,
 FOREIGN KEY(product_id) REFERENCES products(product_id) ON DELETE CASCADE);
CREATE TABLE wishlist(wishlist_id INT AUTO_INCREMENT PRIMARY KEY,user_id INT,product_id INT,created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 UNIQUE KEY uq_wish(user_id,product_id),FOREIGN KEY(user_id) REFERENCES users(user_id) ON DELETE CASCADE,FOREIGN KEY(product_id) REFERENCES products(product_id) ON DELETE CASCADE);
CREATE TABLE addresses(address_id INT AUTO_INCREMENT PRIMARY KEY,user_id INT,address_line VARCHAR(255),city VARCHAR(80),state VARCHAR(80),pincode VARCHAR(10),address_type VARCHAR(30));
CREATE TABLE orders(order_id INT AUTO_INCREMENT PRIMARY KEY,user_id INT,address_id INT,total_amount DECIMAL(10,2),discount DECIMAL(10,2) DEFAULT 0,
 payment_method VARCHAR(30),payment_status VARCHAR(30),order_status VARCHAR(30) DEFAULT 'PLACED',created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 FOREIGN KEY(user_id) REFERENCES users(user_id),FOREIGN KEY(address_id) REFERENCES addresses(address_id));
CREATE TABLE order_items(order_item_id INT AUTO_INCREMENT PRIMARY KEY,order_id INT,product_id INT,quantity INT,price DECIMAL(10,2),
 FOREIGN KEY(order_id) REFERENCES orders(order_id) ON DELETE CASCADE,FOREIGN KEY(product_id) REFERENCES products(product_id));
CREATE TABLE payments(payment_id INT AUTO_INCREMENT PRIMARY KEY,order_id INT,payment_method VARCHAR(30),transaction_reference VARCHAR(100),
 amount DECIMAL(10,2),payment_status VARCHAR(30),created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP);
CREATE TABLE reviews(review_id INT AUTO_INCREMENT PRIMARY KEY,user_id INT,product_id INT,rating INT,review_text TEXT,created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 FOREIGN KEY(user_id) REFERENCES users(user_id),FOREIGN KEY(product_id) REFERENCES products(product_id));
CREATE TABLE coupons(coupon_id INT AUTO_INCREMENT PRIMARY KEY,code VARCHAR(40) UNIQUE,discount_type VARCHAR(20),discount_value DECIMAL(10,2),
 minimum_order DECIMAL(10,2) DEFAULT 0,maximum_discount DECIMAL(10,2),expiry_date DATE,status TINYINT DEFAULT 1);
CREATE TABLE coupon_usage(usage_id INT AUTO_INCREMENT PRIMARY KEY,coupon_id INT,user_id INT,order_id INT,used_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP);

INSERT INTO categories(name,description) VALUES
('Electronics','Smart devices and accessories'),('Fashion','Clothing and lifestyle'),('Gaming','Gaming products'),('Books','Books and learning');

INSERT INTO products(category_id,name,brand,description,price,discount,stock,image_url) VALUES
(1,'Wireless Headphones','SoundMax','Comfortable Bluetooth headphones with deep bass.',2999,15,25,'https://images.unsplash.com/photo-1505740420928-5e560c06d30e?auto=format&fit=crop&w=800&q=80'),
(1,'Smart Watch Pro','TimeX','Fitness tracking and notifications.',4999,20,18,'https://images.unsplash.com/photo-1523275335684-37898b6baf30?auto=format&fit=crop&w=800&q=80'),
(2,'Classic Sneakers','UrbanStep','Everyday casual sneakers.',3499,10,30,'https://images.unsplash.com/photo-1542291026-7eec264c27ff?auto=format&fit=crop&w=800&q=80'),
(3,'Mechanical Keyboard','KeyForge','RGB mechanical keyboard for gaming and coding.',4499,12,12,'https://images.unsplash.com/photo-1587829741301-dc798b83add3?auto=format&fit=crop&w=800&q=80'),
(4,'Java Programming Book','TechPress','Advanced Java reference for students.',899,5,50,'https://images.unsplash.com/photo-1532012197267-da84d127e765?auto=format&fit=crop&w=800&q=80');
