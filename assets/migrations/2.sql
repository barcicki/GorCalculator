ALTER TABLE Tournaments ADD COLUMN Created DATETIME;
UPDATE Tournaments SET Created=DATE('now') WHERE 1=1;