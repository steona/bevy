create database chart;
use chart;

CREATE TABLE piechart (
product_category VARCHAR(32),
bug_count FLOAT
);

CREATE TABLE barchart (
category VARCHAR(32),
series1 FLOAT,
series2 FLOAT,
series3 FLOAT
);

CREATE TABLE timeseries (
date DATE,
series1 FLOAT,
series2 FLOAT,
series3 FLOAT
);


INSERT INTO piechart VALUES ('London', 54.3);
INSERT INTO piechart VALUES ('New York', 43.4);
INSERT INTO piechart VALUES ('Paris', 17.9);


INSERT INTO barchart VALUES ('London', 54.3, 32.1, 53.4);
INSERT INTO barchart VALUES ('New York', 43.4, 54.3, 75.2);
INSERT INTO barchart VALUES ('Paris', 17.9, 34.8, 37.1);


INSERT INTO timeseries VALUES ('2006-05-1', 54.3, 32.1, 53.4);
INSERT INTO timeseries VALUES ('2006-05-1', 43.4, 54.3, 75.2);
INSERT INTO timeseries VALUES ('2006-05-1', 39.6, 55.9, 37.1);
INSERT INTO timeseries VALUES ('2006-05-1', 35.4, 55.2, 27.5);
INSERT INTO timeseries VALUES ('2006-05-1', 33.9, 49.8, 22.3);
INSERT INTO timeseries VALUES ('2006-05-1', 35.2, 48.4, 17.7);
INSERT INTO timeseries VALUES ('2006-05-1', 38.9, 49.7, 15.3);
INSERT INTO timeseries VALUES ('2006-05-1', 36.3, 44.4, 12.1);
INSERT INTO timeseries VALUES ('2006-05-1', 31.0, 46.3, 11.0);