import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
public class ExtractFromJson {
    public static class QueryLog
    {
        @JsonProperty("sqlQuery/time")
        private int queryDuration;
        @JsonProperty("sqlQuery/bytes")
        private int queryBytes;
        @JsonProperty
        private String success;
        @JsonProperty
        private Id context;
        @JsonProperty
        private String identity;
        public QueryLog(){}

        public int getQueryDuration() {
            return queryDuration;
        }

        public int getQueryBytes() {
            return queryBytes;
        }

        public String getIdentity() {
            return identity;
        }

        public String getSuccess() {
            return success;
        }

        public Id getContext() {
            return context;
        }
    }
    public static class Id
    {
        @JsonProperty
        private String sqlQueryId;
        @JsonProperty  //else I was getting unrecognized field error
        private String nativeQueryIds;
        public Id(){}

        public String getSqlQueryId() {
            return sqlQueryId;
        }

        public String getNativeQueryIds() {
            return nativeQueryIds;

        }
    }

    public static void  main(String args[]) throws IOException {

        String queryLog = "2020-06-21T19:48:51.188Z\t10.85.213.34\t\t{\"sqlQuery/time\":152,\"sqlQuery/bytes\":6,\"success\":true,\"context\":{\"sqlQueryId\":\"c8f0ef77-0bb2-4363-abed-e7e460a29229\",\"nativeQueryIds\":\"d1ebcfc9-3fd6-4321-b052-038ad08e1dfe\"},\"identity\":\"allowAll\"}\t{\"query\":\"SELECT COUNT(*) from ims_ns_itemout\",\"context\":{\"sqlQueryId\":\"c8f0ef77-0bb2-4363-abed-e7e460a29229\",\"nativeQueryIds\":\"[d1ebcfc9-3fd6-4321-b052-038ad08e1dfe]\"}}";
        String segment[] = queryLog.split("\t");
        String json = segment[3];
        System.out.println(json);
        ObjectMapper mapper = new ObjectMapper();

        //Method1
        QueryLog mappedValue = mapper.readValue(json,QueryLog.class);
        System.out.println(mappedValue.getQueryDuration());
        System.out.println(mappedValue.getQueryBytes());
        System.out.println(mappedValue.getContext().getSqlQueryId());
        System.out.println(mappedValue.getSuccess());

        //Method 2
        JsonNode jsonNode = mapper.readTree(json);
        System.out.println(jsonNode.get("sqlQuery/time"));
        System.out.println(jsonNode.get("sqlQuery/bytes"));
        System.out.println(jsonNode.get("success"));
        System.out.println(jsonNode.get("context").get("sqlQueryId"));

    }
}
