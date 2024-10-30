INSERT INTO sizes (size_id, name, description, deleted)
VALUES ('03f667f7-4075-41f1-a35d-b4dc71f05b8a', 'Size name', 'Size description', FALSE);

INSERT INTO categories (category_id, name, description, super_category_category_id, deleted)
VALUES ('0bbad5ac-3986-4832-8a87-0141a83052ce', 'Category name', 'Category description', null, false);

INSERT INTO product_images (url, name)
VALUES ('url 1', 'image 1'),
       ('url 2', 'image 2'),
       ('url 3', 'image 3');

INSERT INTO products (product_id, name, description, price, stock, color, size_size_id, category_category_id, deleted)
VALUES ('183f205a-3430-4e11-8bca-57672a0ce3ff', 'Product name', 'Product description', 10000.0, 10, '#ffffff', '03f667f7-4075-41f1-a35d-b4dc71f05b8a', '0bbad5ac-3986-4832-8a87-0141a83052ce', false);

INSERT INTO products_images (products_product_id, images_url)
VALUES ('183f205a-3430-4e11-8bca-57672a0ce3ff', 'url 1'),
       ('183f205a-3430-4e11-8bca-57672a0ce3ff', 'url 2');