package ESOperation;

import java.util.Collection;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import ESOperation.ESCriterion.Operator;  

public class ESSimpleExpression {  
    private String fieldName = "content";       //属性名  默认content
    private Object value;           //对应值  
    private Collection<Object> values;           //对应值  
    private Operator operator;      //计算符  
    private Object from;  
    private Object to;  
  
    protected  ESSimpleExpression(String fieldName, Object value, Operator operator) {  
        this.fieldName = fieldName;  
        this.value = value;  
        this.operator = operator;  
    }  
  
    protected  ESSimpleExpression(String value, Operator operator) {  
        this.value = value;  
        this.operator = operator;  
    }  
  
    protected ESSimpleExpression(String fieldName, Collection<Object> values) {  
        this.fieldName = fieldName;  
        this.values = values;  
        this.operator = Operator.TERMS;  
    }  
  
    protected ESSimpleExpression(String fieldName, Object from, Object to) {  
        this.fieldName = fieldName;  
        this.from = from;  
        this.to = to;  
        this.operator = Operator.RANGE;  
    }  
  
    public QueryBuilder toBuilder() {  
        QueryBuilder qb = null;  
        switch (operator) {  
            case TERM:  
                qb = QueryBuilders.termQuery(fieldName, value);  
                break;  
            case TERMS:  
                qb = QueryBuilders.termsQuery(fieldName, values);  
                break;  
            case RANGE:  
                qb = QueryBuilders.rangeQuery(fieldName).from(from).to(to).includeLower(true).includeUpper(true);  
                break;  
            case FUZZY:  
                qb = QueryBuilders.fuzzyQuery(fieldName, value);  
                break;  
            case QUERY_STRING:  
                qb = QueryBuilders.queryStringQuery(value.toString());
            case MATCH:
            	qb = QueryBuilders.matchQuery(fieldName, value);
        }  
        return qb;  
    }  
}  

