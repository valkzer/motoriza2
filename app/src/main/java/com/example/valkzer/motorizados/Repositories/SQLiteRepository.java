package com.example.valkzer.motorizados.Repositories;

import java.util.Map;
import java.util.List;
import java.util.Objects;
import java.util.HashMap;
import java.util.ArrayList;

abstract class SQLiteRepository {

    private String createStatement = null;
    private String primaryKey      = "id";
    private String tableName       = null;

    SQLiteRepository(String createStatement, String id, String tableName)
    {
        this.createStatement = createStatement;
        this.primaryKey = id;
        this.tableName = tableName;
    }

    Integer insert(String[] fieldList, ArrayList valueList)
    {

        String insertStatement = "INSERT INTO " + this.tableName +
                                 " ( " + this.parseToFieldList(fieldList, true) + " )" +
                                 " VALUES ( " + this.parseToValueList(valueList) + " );";

        return 1;
    }

    Boolean delete(String id) throws Exception
    {
        if (id.isEmpty()) {
            throw new Exception("An id must be provided");
        }
        String deleteStatement = "DELETE FROM " + this.tableName + " WHERE " + this.primaryKey + " = '" + id + "';";
        return true;
    }

    Boolean update(String[] fieldList, ArrayList valueList, String id)
    {
        String insertStatement = "UPDATE " + this.tableName + " SET " +
                                 this.parseToUpdateString(fieldList, valueList) +
                                 " WHERE " + this.primaryKey + " = '" + id + "';";

        return true;
    }

    Map<String, String> find(String id)
    {
        String findStatement = "SELECT * FROM " + this.tableName + " WHERE " + this.primaryKey + " = '" + id + "';";
        return new HashMap<String, String>();
    }

    ArrayList selectAll()
    {
        String selectStatement = "SELECT * FROM " + this.tableName + ";";
        return new ArrayList();
    }

    private String parseToUpdateString(String[] fieldList, ArrayList valueList)
    {
        String fieldListString = "";

        for (int index = 0; index < fieldList.length; index++) {
            String fieldName  = fieldList[index];
            String fieldValue = "null";

            if (valueList.get(index) != null) {
                fieldValue = "'" + valueList.get(index).toString() + "'";
            }

            if (!(Objects.equals(this.primaryKey, fieldName))) {
                fieldListString += fieldName + " = " + fieldValue + ", ";
            }
        }

        if (fieldListString.length() > 2) {
            fieldListString = fieldListString.substring(0, fieldListString.length() - 2);
        }

        return fieldListString;
    }

    private String parseToValueList(List valueList)
    {
        String parsedValueList = "";

        for (int index = 0; index < valueList.size(); index++) {
            if (valueList.get(index) == null) {
                parsedValueList += " null ,";
            } else {
                parsedValueList += " '" + valueList.get(index).toString() + "' ,";
            }
        }

        if (parsedValueList.length() > 2) {
            parsedValueList = parsedValueList.substring(0, parsedValueList.length() - 2);
        }

        return parsedValueList;
    }


    private String parseToFieldList(String[] fieldList, boolean excludePrimaryKey)
    {
        String fieldListString = "";

        for (String aFieldList : fieldList) {
            if (!(Objects.equals(this.primaryKey, aFieldList)) || !excludePrimaryKey) {
                fieldListString += aFieldList + " ,";
            }
        }

        if (fieldListString.length() > 2) {
            fieldListString = fieldListString.substring(0, fieldListString.length() - 2);
        }

        return fieldListString;
    }


}
