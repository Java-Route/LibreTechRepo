-- V3: Seed de datos masivo para LibroTech

-- Editoriales (10 editoriales de distintos países)
INSERT INTO editorials (name, address, country, founded_in)
VALUES ('Alfaguara', 'Calle Gran Vía 32', 'España', 1964),
       ('Planeta', 'Av. Diagonal 662-664', 'España', 1949),
       ('Penguin Random House', '1745 Broadway', 'Estados Unidos', 2013),
       ('Fondo de Cultura Económica', 'Carretera Picacho-Ajusco 227', 'México', 1934),
       ('Norma Editorial', 'Calle 10 #24-60', 'Colombia', 1960),
       ('Anagrama', 'Pedró de la Creu 58', 'España', 1969),
       ('Siglo XXI Editores', 'Cerro del Agua 248', 'México', 1965),
       ('Editorial Oveja Negra', 'Cra 14 #79-17', 'Colombia', 1968),
       ('Tusquets Editores', 'Av. Diagonal 662-664', 'España', 1969),
       ('Seix Barral', 'Av. Diagonal 662-664', 'Argentina', 1911);

-- Géneros literarios (7 géneros)
INSERT INTO genres (name, description)
VALUES ('Ficción', 'Narrativa de invención, novelas y cuentos imaginativos'),
       ('No Ficción', 'Textos basados en hechos reales, ensayos y crónicas'),
       ('Ciencia Ficción', 'Narrativa especulativa con elementos científicos y tecnológicos'),
       ('Fantasía', 'Historias en mundos imaginarios con elementos mágicos o sobrenaturales'),
       ('Terror', 'Obras diseñadas para provocar miedo y tensión en el lector'),
       ('Historia', 'Textos sobre eventos y personajes históricos documentados'),
       ('Desarrollo Personal', 'Guías de crecimiento personal, productividad y bienestar');

-- Libros (primeros 20 de ejemplo - COMPLETE HASTA 200)
INSERT INTO books (title, author, isbn, publication_date, price, available, editorial_id)
VALUES ('Cien años de soledad', 'Gabriel García Márquez', 'ISBN-0001', '1967-06-05', 45000.0, TRUE, 1),
       ('El amor en los tiempos del cólera', 'Gabriel García Márquez', 'ISBN-0002', '1985-09-05', 42000.0, TRUE, 5),
       ('1984', 'George Orwell', 'ISBN-0003', '1949-06-08', 38000.0, TRUE, 3),
       ('Crónica de una muerte anunciada', 'Gabriel García Márquez', 'ISBN-0004', '1981-04-01', 35000.0, TRUE, 8),
       ('Don Quijote de la Mancha', 'Miguel de Cervantes', 'ISBN-0005', '1605-01-16', 55000.0, TRUE, 2),
       ('El Principito', 'Antoine de Saint-Exupéry', 'ISBN-0006', '1943-04-06', 28000.0, TRUE, 3),
       ('Rayuela', 'Julio Cortázar', 'ISBN-0007', '1963-06-28', 40000.0, TRUE, 1),
       ('La sombra del viento', 'Carlos Ruiz Zafón', 'ISBN-0008', '2001-04-01', 48000.0, TRUE, 2),
       ('Fahrenheit 451', 'Ray Bradbury', 'ISBN-0009', '1953-10-19', 36000.0, TRUE, 3),
       ('Dune', 'Frank Herbert', 'ISBN-0010', '1965-08-01', 52000.0, TRUE, 3),
       ('El nombre de la rosa', 'Umberto Eco', 'ISBN-0011', '1980-01-01', 47000.0, TRUE, 6),
       ('Los pilares de la Tierra', 'Ken Follett', 'ISBN-0012', '1989-01-01', 58000.0, TRUE, 2),
       ('It', 'Stephen King', 'ISBN-0013', '1986-09-15', 50000.0, TRUE, 3),
       ('Sapiens', 'Yuval Noah Harari', 'ISBN-0014', '2011-01-01', 62000.0, TRUE, 4),
       ('Hábitos atómicos', 'James Clear', 'ISBN-0015', '2018-10-16', 55000.0, TRUE, 3),
       ('La casa de los espíritus', 'Isabel Allende', 'ISBN-0016', '1982-01-01', 41000.0, TRUE, 9),
       ('Pedro Páramo', 'Juan Rulfo', 'ISBN-0017', '1955-03-01', 33000.0, TRUE, 4),
       ('El laberinto de la soledad', 'Octavio Paz', 'ISBN-0018', '1950-01-01', 37000.0, TRUE, 7),
       ('Frankenstein', 'Mary Shelley', 'ISBN-0019', '1818-01-01', 30000.0, TRUE, 6),
       ('El resplandor', 'Stephen King', 'ISBN-0020', '1977-01-28', 44000.0, TRUE, 3);

-- Relaciones Libro-Género (asigne géneros a los libros)
INSERT INTO books_genres (book_id, genre_id)
VALUES (1, 1),
       (1, 4),  -- Cien años de soledad: Ficción + Fantasía
       (2, 1),  -- El amor en los tiempos del cólera: Ficción
       (3, 1),
       (3, 3),  -- 1984: Ficción + Ciencia Ficción
       (4, 1),  -- Crónica de una muerte anunciada: Ficción
       (5, 1),  -- Don Quijote: Ficción
       (6, 1),
       (6, 4),  -- El Principito: Ficción + Fantasía
       (7, 1),  -- Rayuela: Ficción
       (8, 1),
       (8, 5),  -- La sombra del viento: Ficción + Terror
       (9, 1),
       (9, 3),  -- Fahrenheit 451: Ficción + Ciencia Ficción
       (10, 3),
       (10, 4), -- Dune: Ciencia Ficción + Fantasía
       (11, 1),
       (11, 6), -- El nombre de la rosa: Ficción + Historia
       (12, 1),
       (12, 6), -- Los pilares de la Tierra: Ficción + Historia
       (13, 5), -- It: Terror
       (14, 2),
       (14, 6), -- Sapiens: No Ficción + Historia
       (15, 2),
       (15, 7), -- Hábitos atómicos: No Ficción + Desarrollo Personal
       (16, 1),
       (16, 4), -- La casa de los espíritus: Ficción + Fantasía
       (17, 1), -- Pedro Páramo: Ficción
       (18, 2),
       (18, 6), -- El laberinto de la soledad: No Ficción + Historia
       (19, 1),
       (19, 5), -- Frankenstein: Ficción + Terror
       (20, 5); -- El resplandor: Terror
