package com.example.utils.demo.util;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.*;

import java.util.HashMap;
import java.util.Map;

public class JSqlParserUtil {
    public static void main(String[] args) {
        try {
            Select stmt = (Select) CCJSqlParserUtil.parse("SELECT col1 AS a, col2 AS b, col3 AS c FROM tab,le WHERE col1 = 10 AND col2 = 20 AND col3 = 30");

            Map<String, Expression> map = new HashMap<>();
            for (SelectItem selectItem : ((PlainSelect)stmt.getSelectBody()).getSelectItems()) {
                selectItem.accept(new SelectItemVisitorAdapter() {
                    @Override
                    public void visit(SelectExpressionItem item) {
                        map.put(item.getAlias().getName(), item.getExpression());
                        System.out.println(item.toString());
                    }
                });
            }

            System.out.println("map " + map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
