/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commonUtils;

/**
 *
 * @author tuehm
 */
public class MyPrepareStatement {

    private final Object[] variables;
    private final String queryString;

    public MyPrepareStatement(String query) {
        int count = 0;
        int index = -1;

        while ((index = query.indexOf("?", index + 1)) != -1) {
            count++;
        }
        variables = new Object[count];
        queryString = query.replaceAll("\\?", "%s");
    }

    public void setInt(int parameterIndex, int x) {
        saveObject(parameterIndex, x);
    }

    public void setLong(int parameterIndex, long x) {
        saveObject(parameterIndex, x);
    }

    public void setDouble(int parameterIndex, double x) {
        saveObject(parameterIndex, x);
    }

    public void setString(int parameterIndex, String x) {
        if (x == null) {
            x = "NULL";
        } else {
            x = "'" + x.replaceAll("'", "\\\\'") + "'";
        }
        saveObject(parameterIndex, x);
    }

    private void saveObject(int parameterIndex, Object o){
        if (parameterIndex > variables.length) {
            throw new IndexOutOfBoundsException("");
        }
        variables[parameterIndex - 1] = o;
    }

    @Override
    public String toString() {
        return String.format(queryString, variables);
    }

}
