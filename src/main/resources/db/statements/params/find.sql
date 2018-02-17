SELECT
  "object_id",
  "attr_id",
  "number_val",
  "text_val",
  "date_val",
  (EXTRACT(DAY FROM "interval_val") * 86400
   + EXTRACT(HOUR FROM "interval_val") * 3600
   + EXTRACT(MINUTE FROM "interval_val") * 60
   + EXTRACT(SECOND FROM "interval_val")) * 1E9 AS "interval_val",
  "boolean_val"
FROM "params"
WHERE "object_id" = ?