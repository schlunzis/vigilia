select null                                                                   as TABLE_CAT,
       null                                                                   as TABLE_SCHEM,
       tblname                                                                as TABLE_NAME,
       cn                                                                     as COLUMN_NAME,
       ct                                                                     as DATA_TYPE,
       tn                                                                     as TYPE_NAME,
       colSize                                                                as COLUMN_SIZE,
       2000000000                                                             as BUFFER_LENGTH,
       colDecimalDigits                                                       as DECIMAL_DIGITS,
       10                                                                     as NUM_PREC_RADIX,
       colnullable                                                            as NULLABLE,
       null                                                                   as REMARKS,
       colDefault                                                             as COLUMN_DEF,
       0                                                                      as SQL_DATA_TYPE,
       0                                                                      as SQL_DATETIME_SUB,
       2000000000                                                             as CHAR_OCTET_LENGTH,
       ordpos                                                                 as ORDINAL_POSITION,
       (case colnullable when 0 then 'NO' when 1 then 'YES' else '' end)      as IS_NULLABLE,
       null                                                                   as SCOPE_CATALOG,
       null                                                                   as SCOPE_SCHEMA,
       null                                                                   as SCOPE_TABLE,
       null                                                                   as SOURCE_DATA_TYPE,
       (case colautoincrement when 0 then 'NO' when 1 then 'YES' else '' end) as IS_AUTOINCREMENT,
       (case colgenerated when 0 then 'NO' when 1 then 'YES' else '' end)     as IS_GENERATEDCOLUMN
from (select 1               as ordpos,
             1               as colnullable,
             12              as ct,
             2000000000      as colSize,
             0               as colDecimalDigits,
             'sqlite_schema' as tblname,
             'type'          as cn,
             'TEXT'          as tn,
             null            as colDefault,
             0               as colautoincrement,
             0               as colgenerated
      where upper(cn) like upper('%') ESCAPE '\'
      union all
      select 2               as ordpos,
             1               as colnullable,
             12              as ct,
             2000000000      as colSize,
             0               as colDecimalDigits,
             'sqlite_schema' as tblname,
             'name'          as cn,
             'TEXT'          as tn,
             null            as colDefault,
             0               as colautoincrement,
             0               as colgenerated
      where upper(cn) like upper('%') ESCAPE '\'
      union all
      select 3               as ordpos,
             1               as colnullable,
             12              as ct,
             2000000000      as colSize,
             0               as colDecimalDigits,
             'sqlite_schema' as tblname,
             'tbl_name'      as cn,
             'TEXT'          as tn,
             null            as colDefault,
             0               as colautoincrement,
             0               as colgenerated
      where upper(cn) like upper('%') ESCAPE '\'
      union all
      select 4               as ordpos,
             1               as colnullable,
             4               as ct,
             2000000000      as colSize,
             0               as colDecimalDigits,
             'sqlite_schema' as tblname,
             'rootpage'      as cn,
             'INT'           as tn,
             null            as colDefault,
             0               as colautoincrement,
             0               as colgenerated
      where upper(cn) like upper('%') ESCAPE '\'
      union all
      select 5               as ordpos,
             1               as colnullable,
             12              as ct,
             2000000000      as colSize,
             0               as colDecimalDigits,
             'sqlite_schema' as tblname,
             'sql'           as cn,
             'TEXT'          as tn,
             null            as colDefault,
             0               as colautoincrement,
             0               as colgenerated
      where upper(cn) like upper('%') ESCAPE '\'
      union all
      select 1                 as ordpos,
             1                 as colnullable,
             4                 as ct,
             2000000000        as colSize,
             0                 as colDecimalDigits,
             'HTE_file_source' as tblname,
             'added_date_tz'   as cn,
             'INTEGER'         as tn,
             null              as colDefault,
             0                 as colautoincrement,
             0                 as colgenerated
      where upper(cn) like upper('%') ESCAPE '\'
      union all
      select 2                      as ordpos,
             1                      as colnullable,
             4                      as ct,
             2000000000             as colSize,
             0                      as colDecimalDigits,
             'HTE_file_source'      as tblname,
             'last_updated_date_tz' as cn,
             'INTEGER'              as tn,
             null                   as colDefault,
             0                      as colautoincrement,
             0                      as colgenerated
      where upper(cn) like upper('%') ESCAPE '\'
      union all
      select 3                 as ordpos,
             1                 as colnullable,
             12                as ct,
             2000000000        as colSize,
             10                as colDecimalDigits,
             'HTE_file_source' as tblname,
             'added_date'      as cn,
             'TIMESTAMP'       as tn,
             null              as colDefault,
             0                 as colautoincrement,
             0                 as colgenerated
      where upper(cn) like upper('%') ESCAPE '\'
      union all
      select 4                   as ordpos,
             1                   as colnullable,
             12                  as ct,
             2000000000          as colSize,
             10                  as colDecimalDigits,
             'HTE_file_source'   as tblname,
             'last_updated_date' as cn,
             'TIMESTAMP'         as tn,
             null                as colDefault,
             0                   as colautoincrement,
             0                   as colgenerated
      where upper(cn) like upper('%') ESCAPE '\'
      union all
      select 5                 as ordpos,
             0                 as colnullable,
             12                as ct,
             2000000000        as colSize,
             0                 as colDecimalDigits,
             'HTE_file_source' as tblname,
             'id'              as cn,
             'BLOB'            as tn,
             null              as colDefault,
             0                 as colautoincrement,
             0                 as colgenerated
      where upper(cn) like upper('%') ESCAPE '\'
      union all
      select 6                 as ordpos,
             0                 as colnullable,
             12                as ct,
             36                as colSize,
             0                 as colDecimalDigits,
             'HTE_file_source' as tblname,
             'hib_sess_id'     as cn,
             'CHAR'            as tn,
             null              as colDefault,
             0                 as colautoincrement,
             0                 as colgenerated
      where upper(cn) like upper('%') ESCAPE '\'
      union all
      select 7                     as ordpos,
             1                     as colnullable,
             12                    as ct,
             2000000000            as colSize,
             0                     as colDecimalDigits,
             'HTE_file_source'     as tblname,
             'additional_metadata' as cn,
             'CLOB'                as tn,
             null                  as colDefault,
             0                     as colautoincrement,
             0                     as colgenerated
      where upper(cn) like upper('%') ESCAPE '\'
      union all
      select 8                        as ordpos,
             1                        as colnullable,
             12                       as ct,
             2000000000               as colSize,
             0                        as colDecimalDigits,
             'HTE_file_source'        as tblname,
             'indexed_with_reader_id' as cn,
             'BLOB'                   as tn,
             null                     as colDefault,
             0                        as colautoincrement,
             0                        as colgenerated
      where upper(cn) like upper('%') ESCAPE '\'
      union all
      select 9                 as ordpos,
             1                 as colnullable,
             12                as ct,
             2000000000        as colSize,
             0                 as colDecimalDigits,
             'HTE_file_source' as tblname,
             'path'            as cn,
             'TEXT'            as tn,
             null              as colDefault,
             0                 as colautoincrement,
             0                 as colgenerated
      where upper(cn) like upper('%') ESCAPE '\'
      union all
      select 10                as ordpos,
             1                 as colnullable,
             12                as ct,
             255               as colSize,
             0                 as colDecimalDigits,
             'HTE_file_source' as tblname,
             'title'           as cn,
             'VARCHAR'         as tn,
             null              as colDefault,
             0                 as colautoincrement,
             0                 as colgenerated
      where upper(cn) like upper('%') ESCAPE '\'
      union all
      select 11                as ordpos,
             1                 as colnullable,
             12                as ct,
             2000000000        as colSize,
             0                 as colDecimalDigits,
             'HTE_file_source' as tblname,
             'url'             as cn,
             'TEXT'            as tn,
             null              as colDefault,
             0                 as colautoincrement,
             0                 as colgenerated
      where upper(cn) like upper('%') ESCAPE '\'
      union all
      select 1                as ordpos,
             0                as colnullable,
             12               as ct,
             2000000000       as colSize,
             0                as colDecimalDigits,
             'HT_file_source' as tblname,
             'id'             as cn,
             'BLOB'           as tn,
             null             as colDefault,
             0                as colautoincrement,
             0                as colgenerated
      where upper(cn) like upper('%') ESCAPE '\'
      union all
      select 2                as ordpos,
             0                as colnullable,
             12               as ct,
             36               as colSize,
             0                as colDecimalDigits,
             'HT_file_source' as tblname,
             'hib_sess_id'    as cn,
             'CHAR'           as tn,
             null             as colDefault,
             0                as colautoincrement,
             0                as colgenerated
      where upper(cn) like upper('%') ESCAPE '\')
order by TABLE_SCHEM, TABLE_NAME, ORDINAL_POSITION;
