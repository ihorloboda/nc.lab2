UPDATE "params"
SET "number_val" = ?,
  "text_val"     = ?,
  "date_val"     = ?,
  "interval_val" = NUMTODSINTERVAL(? / 1E9, 'SECOND'),
  "boolean_val"  = ?
WHERE "object_id" = ? AND "attr_id" = ?