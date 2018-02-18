INSERT INTO "params" ("object_id", "attr_id", "number_val", "text_val", "date_val", "interval_val", "boolean_val")
VALUES
  (?, ?, ?, ?, ?, NUMTODSINTERVAL(? / 1E9, 'SECOND'), ?)
--For inserting interval use nanoseconds