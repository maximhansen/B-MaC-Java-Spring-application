INSERT INTO product(product_number, product_name, description, price, is_deactivated)
VALUES ('6f8d5a2b-aa4e-4b71-a33d-182ccda1d3d0', 'Croissant', 'lekkere franse croissant', 2, false);

INSERT INTO product(product_number, product_name, description, price, is_deactivated)
VALUES ('4c8e21c3-5da9-43e4-b1aa-07e6de0f2f3a', 'Chocolade donut', 'lekkere donut met chocolade', 1.49, false);

INSERT INTO product(product_number, product_name, description, price, is_deactivated)
VALUES ('f28e9a06-0d2c-4a3b-b12d-7a1c1f3ec1e2', 'Baguette', 'Freshly baked French baguette', 3.49, false);


INSERT INTO recipe(recipe_number, product_product_number, is_finalised)
VALUES (gen_random_uuid(), '6f8d5a2b-aa4e-4b71-a33d-182ccda1d3d0', true);

INSERT INTO recipe(recipe_number, product_product_number, is_finalised)
VALUES (gen_random_uuid(), '4c8e21c3-5da9-43e4-b1aa-07e6de0f2f3a', true);

INSERT INTO recipe(recipe_number, product_product_number, is_finalised)
VALUES (gen_random_uuid(), 'f28e9a06-0d2c-4a3b-b12d-7a1c1f3ec1e2', true);

INSERT INTO ingredient(id, ingredient_number, name, amount, product_number) values (gen_random_uuid(), 'a29526f9-6a3b-4f2c-b9ed-94a0b7c8f014', 'Flour', 10, (SELECT product_number FROM product WHERE product_name = 'Croissant'));
INSERT INTO ingredient(id, ingredient_number, name, amount, product_number) values (gen_random_uuid(), 'e92a29d9-041b-4e9a-8c42-9c5f3bea3837', 'Butter', 10, (SELECT product_number FROM product WHERE product_name = 'Croissant'));
INSERT INTO ingredient(id, ingredient_number, name, amount, product_number) values (gen_random_uuid(), 'f21b8bb6-0ab7-4cc7-9b52-717200d9f662', 'Baking Powder', 1, (SELECT product_number FROM product WHERE product_name = 'Croissant'));

INSERT INTO ingredient(id, ingredient_number, name, amount, product_number) values (gen_random_uuid(), 'f21b8bb6-0ab7-4cc7-9b52-717200d9f662', 'Baking Powder', 3, (SELECT product_number FROM product WHERE product_name = 'Chocolade donut'));
INSERT INTO ingredient(id, ingredient_number, name, amount, product_number) values (gen_random_uuid(), 'a29526f9-6a3b-4f2c-b9ed-94a0b7c8f014', 'Flour', 2, (SELECT product_number FROM product WHERE product_name = 'Chocolade donut'));
INSERT INTO ingredient(id, ingredient_number, name, amount, product_number) values (gen_random_uuid(), '19a4eb19-49d0-4e8a-b07a-177d3ea4287e', 'Salt', 2, (SELECT product_number FROM product WHERE product_name = 'Chocolade donut'));
INSERT INTO ingredient(id, ingredient_number, name, amount, product_number) values (gen_random_uuid(), '786c0eb5-042b-4f6c-b2c3-ee2933e36ed0', 'Milk', 1, (SELECT product_number FROM product WHERE product_name = 'Chocolade donut'));
INSERT INTO ingredient(id, ingredient_number, name, amount, product_number) values (gen_random_uuid(), '54dd2e1a-5d17-4e5d-9a35-0f70ec36ac3f', 'Eggs', 2, (SELECT product_number FROM product WHERE product_name = 'Chocolade donut'));
INSERT INTO ingredient(id, ingredient_number, name, amount, product_number) values (gen_random_uuid(), 'e92a29d9-041b-4e9a-8c42-9c5f3bea3837', 'Butter', 0.5, (SELECT product_number FROM product WHERE product_name = 'Chocolade donut'));
INSERT INTO ingredient(id, ingredient_number, name, amount, product_number) values (gen_random_uuid(), 'c0d5247a-e7f1-485f-99c5-3f9c3a289ee5', 'Vanilla Extract', 0.1, (SELECT product_number FROM product WHERE product_name = 'Chocolade donut'));
INSERT INTO ingredient(id, ingredient_number, name, amount, product_number) values (gen_random_uuid(), 'd0d5247a-7ef1-485f-99c5-3f9c3a289ee9', 'Chocolate', 0.5, (SELECT product_number FROM product WHERE product_name = 'Chocolade donut'));

INSERT INTO ingredient(id, ingredient_number, name, amount, product_number) values (gen_random_uuid(), 'a29526f9-6a3b-4f2c-b9ed-94a0b7c8f014', 'Flour', 1, (SELECT product_number FROM product WHERE product_name = 'Baguette'));
INSERT INTO ingredient(id, ingredient_number, name, amount, product_number) values (gen_random_uuid(), 'a6e362f3-7614-45b6-a141-66ce729e991e', 'Yeast', 0.5, (SELECT product_number FROM product WHERE product_name = 'Baguette'));
INSERT INTO ingredient(id, ingredient_number, name, amount, product_number) values (gen_random_uuid(), '19a4eb19-49d0-4e8a-b07a-177d3ea4287e', 'Salt', 0.5, (SELECT product_number FROM product WHERE product_name = 'Baguette'));


INSERT INTO recipe_steps(recipe_id, step) VALUES ((SELECT recipe_number FROM recipe WHERE product_product_number = (SELECT product_number FROM product WHERE product_name = 'Croissant')), 'Meng de bloem, boter, gist en melk in een kom');
INSERT INTO recipe_steps(recipe_id, step) VALUES ((SELECT recipe_number FROM recipe WHERE product_product_number = (SELECT product_number FROM product WHERE product_name = 'Croissant')), 'Kneed het deeg tot een bal');
INSERT INTO recipe_steps(recipe_id, step) VALUES ((SELECT recipe_number FROM recipe WHERE product_product_number = (SELECT product_number FROM product WHERE product_name = 'Croissant')), 'Laat het deeg 30 minuten rusten');
INSERT INTO recipe_steps(recipe_id, step) VALUES ((SELECT recipe_number FROM recipe WHERE product_product_number = (SELECT product_number FROM product WHERE product_name = 'Croissant')), 'Rol de croissantjes op en smeer ze in met boter');
INSERT INTO recipe_steps(recipe_id, step) VALUES ((SELECT recipe_number FROM recipe WHERE product_product_number = (SELECT product_number FROM product WHERE product_name = 'Croissant')), 'Bak de croissantjes in de oven op 180 graden voor 15 minuten');

INSERT INTO recipe_steps(recipe_id, step) VALUES ((SELECT recipe_number FROM recipe WHERE product_product_number = (SELECT product_number FROM product WHERE product_name = 'Chocolade donut')), 'Meng 2 kopjes bloem, 1/2 kopje suiker, 2 theelepels bakpoeder en 1/2 theelepel zout in een grote kom.');
INSERT INTO recipe_steps(recipe_id, step) VALUES ((SELECT recipe_number FROM recipe WHERE product_product_number = (SELECT product_number FROM product WHERE product_name = 'Chocolade donut')), 'Klop in een andere kom 2 eieren en voeg 3/4 kopje melk, 2 eetlepels gesmolten boter en 1 theelepel vanille-extract toe. Meng goed.');
INSERT INTO recipe_steps(recipe_id, step) VALUES ((SELECT recipe_number FROM recipe WHERE product_product_number = (SELECT product_number FROM product WHERE product_name = 'Chocolade donut')), 'Giet het natte mengsel in de droge ingrediënten en roer tot alles goed is gecombineerd. Het deeg moet dik, maar nog steeds schepbaar zijn.');
INSERT INTO recipe_steps(recipe_id, step) VALUES ((SELECT recipe_number FROM recipe WHERE product_product_number = (SELECT product_number FROM product WHERE product_name = 'Chocolade donut')), 'Vul elke donutvorm met het deeg. Zorg ervoor dat je ze niet helemaal vol doet, omdat ze zullen uitzetten tijdens het bakken.');
INSERT INTO recipe_steps(recipe_id, step) VALUES ((SELECT recipe_number FROM recipe WHERE product_product_number = (SELECT product_number FROM product WHERE product_name = 'Chocolade donut')), 'Bak de donuts in de voorverwarmde oven op 180°C gedurende ongeveer 10-12 minuten, of tot ze veerkrachtig aanvoelen wanneer je er zachtjes op drukt.');
INSERT INTO recipe_steps(recipe_id, step) VALUES ((SELECT recipe_number FROM recipe WHERE product_product_number = (SELECT product_number FROM product WHERE product_name = 'Chocolade donut')), 'Terwijl de donuts afkoelen, maak je het glazuur. Meng 1 kopje poedersuiker, 2 eetlepels cacaopoeder, 3-4 eetlepels melk en 1 theelepel vanille-extract in een kom tot een gladde glazuur.');
INSERT INTO recipe_steps(recipe_id, step) VALUES ((SELECT recipe_number FROM recipe WHERE product_product_number = (SELECT product_number FROM product WHERE product_name = 'Chocolade donut')), 'Doop elke donut in het glazuur en zorg ervoor dat de bovenkant gelijkmatig bedekt is. Laat overtollig glazuur eraf druipen.');
INSERT INTO recipe_steps(recipe_id, step) VALUES ((SELECT recipe_number FROM recipe WHERE product_product_number = (SELECT product_number FROM product WHERE product_name = 'Chocolade donut')), 'Laat de glazuur hard worden voordat je van je zelfgemaakte chocoladedonuts geniet!');

INSERT INTO recipe_steps(recipe_id, step) VALUES ((SELECT recipe_number FROM recipe WHERE product_product_number = (SELECT product_number FROM product WHERE product_name = 'Baguette')), 'Meng bloem, water, gist en zout in een kom.');
INSERT INTO recipe_steps(recipe_id, step) VALUES ((SELECT recipe_number FROM recipe WHERE product_product_number = (SELECT product_number FROM product WHERE product_name = 'Baguette')), 'Kneed het deeg en laat het 1 uur rijzen.');
INSERT INTO recipe_steps(recipe_id, step) VALUES ((SELECT recipe_number FROM recipe WHERE product_product_number = (SELECT product_number FROM product WHERE product_name = 'Baguette')), 'Vorm het deeg tot een baguette.');
INSERT INTO recipe_steps(recipe_id, step) VALUES ((SELECT recipe_number FROM recipe WHERE product_product_number = (SELECT product_number FROM product WHERE product_name = 'Baguette')), 'Laat de baguette 30 minuten rijzen.');
INSERT INTO recipe_steps(recipe_id, step) VALUES ((SELECT recipe_number FROM recipe WHERE product_product_number = (SELECT product_number FROM product WHERE product_name = 'Baguette')), 'Bak de baguette in de oven op 220°C gedurende 20-25 minuten.');
INSERT INTO recipe_steps(recipe_id, step) VALUES ((SELECT recipe_number FROM recipe WHERE product_product_number = (SELECT product_number FROM product WHERE product_name = 'Baguette')), 'Laat de baguette afkoelen en geniet van je zelfgemaakte baguette!');



