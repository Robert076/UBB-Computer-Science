use Supplements;

/*
---------------------------------------------------------------------------------------------
---------------------------------------- LABORATOR 1 ----------------------------------------
---------------------------------------------------------------------------------------------
*/

CREATE TABLE Customers (
	CustomerID int primary key auto_increment,
    CustomerName varchar(255) not null default "",
    CustomerEmail varchar(255) not null default "example@mail.com",
    CustomerPhone varchar(20) not null default "",
    CustomerDateOfBirth date not null
);

CREATE TABLE PaymentMethods (
	PaymentMethodID int primary key auto_increment,
    MethodName varchar(255) not null
);

CREATE TABLE Shipping (
	ShippingID int primary key auto_increment,
    ShippingMethod varchar(255) not null default "",
    ShippingCost decimal(5, 2) not null default 0.00
);

CREATE TABLE Orders (
	OrderID int primary key auto_increment,
    CustomerID int not null, -- clientul care a comandat
    constraint FK_OrdersCustomer foreign key(CustomerID) references Customers(CustomerID)
		on delete cascade, -- daca sterg un customer, linia asta face sa se stearga toate comenzile lui
	PaymentMethodID int not null, -- cu ce a platit clientul aceasta comanda
    constraint FK_OrdersPayment foreign key(PaymentMethodID) references PaymentMethods(PaymentMethodID)
		on delete cascade,
	ShippingID int not null, -- datele despre livrare ale acestei comenzi
	constraint FK_OrdersShipping foreign key(ShippingID) references Shipping(ShippingID)
		on delete cascade
);

CREATE TABLE Suppliers (
	SupplierID int primary key auto_increment,
    SupplierName varchar(255) not null default "",
    SupplierPhone varchar(20) not null default ""
);

CREATE TABLE Supplements (
	SupplementID int primary key auto_increment,
    SupplementName varchar(255) not null default "",
    SupplementDescription varchar(255) not null default "",
    SupplierID int not null, -- fiecare supliment are pe cineva ce il aduce
    constraint FK_Supplements foreign key(SupplierID) references Suppliers(SupplierID)
		on delete cascade,
    SupplementPrice decimal(5, 2) not null,
	SupplementStock int not null
);

CREATE TABLE Categories (
	CategoryID int primary key auto_increment,
    CategoryName varchar(255) not null default "",
    CategoryDescription varchar(255) not null default "" 
);

CREATE TABLE OrderItems ( -- aici tin minte toate produsele comandate din fiecare comanda
	OrderItemID int primary key auto_increment,
    OrderID int not null, -- fiecare item din orderItems apartine de un order din orders
    constraint FK_OrderItems foreign key(OrderID) references Orders(OrderID)
		on delete cascade,
    SupplementID int not null, -- fiecare item din comanda are si un supplement id pentru ca noi avem suplimente ca si produse.
    constraint FK_OrderItemsSupplement foreign key(SupplementID) references Supplements(SupplementID)
		on delete cascade
);

CREATE TABLE Ratings (
	RatingID int primary key auto_increment,
    CustomerID int not null, -- fiecare rating e lasat de un customer
    constraint FK_RatingsCustomer foreign key(CustomerID) references Customers(CustomerID)
		on delete cascade,
	SupplementID int not null, -- fiecare rating este pentru un supliment
    constraint FK_RatingsSupplement foreign key(SupplementID) references Supplements(SupplementID)
		on delete cascade,
	RatingValue int not null default 0
);

CREATE TABLE Stores (
	StoreID int primary key auto_increment,
    StoreName varchar(255) not null,
    StoreAddress varchar(255) not null,
    StorePhone varchar(20) not null
);

CREATE TABLE SupplementCategories ( -- tabel de legatura pt many to many
	SupplementID int not null, 
    constraint FK_SupplementCategoriesSupplement foreign key(SupplementID) references Supplements(SupplementID)
		on delete cascade,
	CategoryID int not null, 
    constraint FK_SupplementCategoriesCategory foreign key(CategoryID) references Categories(CategoryID)
		on delete cascade
);


/*
---------------------------------------------------------------------------------------------
---------------------------------------- LABORATOR 2 ----------------------------------------
---------------------------------------------------------------------------------------------
*/

SET SQL_SAFE_UPDATES = 0;

-- Customers table --

INSERT INTO Customers(CustomerName, CustomerEmail, CustomerPhone, CustomerDateOfBirth) VALUES('Caleb Siggy', 'caleb.siggy@ddi.ro', '0733913222', STR_TO_DATE('24-10-2004', '%d-%m-%Y'));
INSERT INTO Customers(CustomerName, CustomerEmail, CustomerPhone, CustomerDateOfBirth) VALUES('Robby Rupe', 'roby.rupe@tot.ro', '0752593584', STR_TO_DATE('06-07-2004', '%d-%m-%Y'));
INSERT INTO Customers(CustomerName, CustomerEmail, CustomerPhone, CustomerDateOfBirth) VALUES('Robby Rupe', '', '0752593584', STR_TO_DATE('06-07-2004', '%d-%m-%Y'));
UPDATE Customers SET CustomerName='Roby rupe' WHERE CustomerEmail='roby.rupe@tot.ro';
SELECT * FROM Customers;
DELETE FROM Customers WHERE CustomerName='Robby Rupe' AND CustomerEmail='';

-- Stores table --

INSERT INTO Stores(StoreName, StoreAddress, StorePhone) VALUES('', ''); -- error because all fields are marked as not null
INSERT INTO Stores(StoreName, StoreAddress, StorePhone) VALUES('Supplement Market', 'Iulius Mall', '0733444232');
UPDATE Stores SET StoreName='Supplement Shop' WHERE StoreName='Supplement Market';
DELETE FROM Stores WHERE StoreID >= 0 AND StoreName IS NOT NULL;
SELECT * FROM Stores;

-- Categories table --

INSERT INTO Categories(CategoryName, CategoryDescription) VALUES('Minerale', 'Minerale necesare corpului uman');
UPDATE Categories SET CategoryDescription='Minerale absolut vitale corpului uman' WHERE CategoryName='Minerale';
SELECT * FROM Categories;

-- PaymentMethods table --

INSERT INTO PaymentMethods(MethodName) VALUES('Card');
UPDATE PaymentMethods SET MethodName='Cash' WHERE PaymentMethodID=1;
SELECT * FROM PaymentMethods;


-- a

SELECT * FROM Customers WHERE CustomerID = 1
UNION
SELECT * FROM Customers WHERE CustomerID = 2;

SELECT * FROM Customers WHERE CustomerID = 1
UNION ALL
SELECT * FROM Customers WHERE CustomerID = 2;

SELECT CustomerID FROM Customers WHERE CustomerName != 'RobbyRupe' OR CustomerName != 'Caleb Siggy';

-- b

INSERT INTO Suppliers(SupplierName, SupplierPhone) VALUES('Caleb Siggy', '0744950134');

SELECT CustomerName FROM Customers
INNER JOIN Suppliers
ON SupplierName = CustomerName; -- ne da valorile comune adica Caleb Siggy

SELECT CustomerName FROM Customers WHERE CustomerName IN ('NU EXISTAAA');
SELECT CustomerName FROM Customers WHERE CustomerName IN ('NU EXISTAAA');

-- c

SELECT CustomerName FROM Customers WHERE CustomerName NOT IN('Caleb Siggy');
