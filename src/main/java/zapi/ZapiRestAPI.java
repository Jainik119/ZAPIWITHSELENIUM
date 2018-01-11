package zapi;

import extendreport.Listner.TestListener;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.Map;

/**
 * @author Jainik Bakaraniya 12/29/2017
 * Please Ignore this class this class have non use ZAPI call
 */
public class ZapiRestAPI extends Zapi{


    /*
    public static String createCycleUri = "/public/rest/api/1.0/cycle?expand=&clonedCycleId=";
    public static String addTestsUri = "/public/rest/api/1.0/executions/add/cycle/";
    public static String createExecution = "/public/rest/api/1.0/execution";.
    public static String getTestDetails = "/public/rest/api/1.0/teststep/";

    public void getExecution()
            throws URISyntaxException, JSONException {
        Map<String, String> executionIds = new HashMap<String, String>();
        ZFJCloudRestClient client = ZFJCloudRestClient.restBuilder(zephyrBaseUrl, accessKey, secKey, username).build();
        String exeId = "0001515069354754-242ac112-0001";
        long issueId = 10000;
        long proId = Long.parseLong(projectId);
        // String url = zephyrBaseUrl + getExecution + exeId +"?projectId="+proId+"&issueId="+issueId;
        String url = zephyrBaseUrl + getExecution + cycleId + "?projectId="
                + projectId + "&versionId=" + versionId;

        URI uri = new URI(url);
        System.out.println(uri);
        int expirationInSec = 360;
        JwtGenerator jwtGenerator = client.getJwtGenerator();
        String jwt = jwtGenerator.generateJWT("GET", uri, expirationInSec);

        HttpResponse response = null;
        HttpClient restClient = new DefaultHttpClient();

        HttpGet addTestsReq = new HttpGet(uri);
        addTestsReq.addHeader("Content-Type", "application/json");
        addTestsReq.addHeader("Authorization", jwt);
        addTestsReq.addHeader("zapiAccessKey", accessKey);

        try {
            response = restClient.execute(addTestsReq);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode >= 200 && statusCode < 300) {
            System.out.println("Sucessfully Executed");
        } else {
            try {
                throw new ClientProtocolException("Unexpected response status: " + statusCode);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            }
        }
    }

     public void createCycle(StringEntity cycleJSON)
            throws URISyntaxException, JSONException {
        ZFJCloudRestClient client = ZFJCloudRestClient.restBuilder(zephyrBaseUrl, accessKey, secKey, username).build();
        String url = zephyrBaseUrl + createCycleUri;
        URI uri = new URI(url);
        int expirationInSec = 360;
        JwtGenerator jwtGenerator = client.getJwtGenerator();
        String jwt = jwtGenerator.generateJWT("POST", uri, expirationInSec);

        HttpResponse response = null;
        HttpClient restClient = new DefaultHttpClient();

        HttpPost createCycleReq = new HttpPost(uri);
        createCycleReq.addHeader("Content-Type", "application/json");
        createCycleReq.addHeader("Authorization", jwt);
        createCycleReq.addHeader("zapiAccessKey", accessKey);
        createCycleReq.setEntity(cycleJSON);

        try {
            response = restClient.execute(createCycleReq);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println(statusCode);
        String cycleId = "-1";
        if (statusCode >= 200 && statusCode < 300) {
            HttpEntity entity = response.getEntity();
            String string = null;
            try {
                string = EntityUtils.toString(entity);
                JSONObject cycleObj = new JSONObject(string);
                cycleId = cycleObj.getString("id");
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                throw new ClientProtocolException("Unexpected response status: " + statusCode);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            }
        }
        System.out.println("cycleId" + cycleId);
    }

    public void addTestsToCycle(StringEntity addTestsJSON) throws URISyntaxException, JSONException, IllegalStateException, IOException {
        ZFJCloudRestClient client = ZFJCloudRestClient.restBuilder(zephyrBaseUrl, accessKey, secKey, username).build();
        String url = zephyrBaseUrl + addTestsUri + cycleId;
        URI uri = new URI(url);
        int expirationInSec = 360;
        JwtGenerator jwtGenerator = client.getJwtGenerator();
        String jwt = jwtGenerator.generateJWT("POST", uri, expirationInSec);

        HttpResponse response = null;
        HttpClient restClient = new DefaultHttpClient();

        HttpPost addTestsReq = new HttpPost(uri);
        addTestsReq.addHeader("Content-Type", "application/json");
        addTestsReq.addHeader("Authorization", jwt);
        addTestsReq.addHeader("zapiAccessKey", accessKey);
        addTestsReq.setEntity(addTestsJSON);

        try {
            response = restClient.execute(addTestsReq);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode >= 200 && statusCode < 300) {
            System.out.println("Sucessfully added");
        } else {
            try {
                throw new ClientProtocolException("Unexpected response status: " + statusCode);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            }
        }
    }

    public void createExecution(StringEntity executionJSON)
            throws URISyntaxException, JSONException {

        ZFJCloudRestClient client = ZFJCloudRestClient.restBuilder(zephyrBaseUrl, accessKey, secKey, username).build();
        String url = zephyrBaseUrl + createExecution;
        URI uri = new URI(url);
        int expirationInSec = 360;
        JwtGenerator jwtGenerator = client.getJwtGenerator();
        String jwt = jwtGenerator.generateJWT("POST", uri, expirationInSec);

        HttpResponse response = null;
        HttpClient restClient = new DefaultHttpClient();

        HttpPost addTestsReq = new HttpPost(uri);
        addTestsReq.addHeader("Content-Type", "application/json");
        addTestsReq.addHeader("Authorization", jwt);
        addTestsReq.addHeader("zapiAccessKey", accessKey);
        addTestsReq.setEntity(executionJSON);

        try {
            response = restClient.execute(addTestsReq);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode >= 200 && statusCode < 300) {
            System.out.println("Sucessfully Executed");
        } else {
            try {
                throw new ClientProtocolException("Unexpected response status: " + statusCode);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            }
        }
    }

    */

      /*public void getTestStepData() throws URISyntaxException {
        Map<String, String> executionIds = new HashMap<String, String>();
        //String baseURL = "https://private-anon-c0d973bf74-getzephyr.apiary-proxy.com/" ;
        ZFJCloudRestClient client = ZFJCloudRestClient.restBuilder(zephyrBaseUrl, accessKey, secKey, username).build();
        long issueId = 10000;
        String exeId = "0001515069354754-242ac112-0001";
        boolean isOrderder = true;
        long project = Long.parseLong(projectId);
        String url = zephyrBaseUrl + getTestDetails + issueId + "?projectId=" + project;


        URI uri = new URI(url);
        System.out.println(uri);
        int expirationInSec = 360;
        JwtGenerator jwtGenerator = client.getJwtGenerator();
        String jwt = jwtGenerator.generateJWT("GET", uri, expirationInSec);
        System.out.println(jwt);
        HttpResponse response = null;
        HttpClient restClient = new DefaultHttpClient();

        HttpGet addTestsReq = new HttpGet(uri);
        addTestsReq.addHeader("Content-Type", "application/json");
        addTestsReq.addHeader("Authorization", jwt);
        addTestsReq.addHeader("zapiAccessKey", accessKey);

        try {
            response = restClient.execute(addTestsReq);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode >= 200 && statusCode < 300) {
            String string1 = null;
            HttpEntity entity1 = response.getEntity();
            System.out.println("Sucessfully Executed");
            try {
                string1 = EntityUtils.toString(entity1);
                System.out.println(string1);
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            JSONObject allIssues = new JSONObject(string1);
            //JSONArray IssuesArray = allIssues.getJSONArray("searchObjectList");
            System.out.println(allIssues);
        }
    }*/

   /* public void getTestStepDataByID() throws URISyntaxException {
        Map<String, String> executionIds = new HashMap<String, String>();
        //String baseURL = "https://private-anon-c0d973bf74-getzephyr.apiary-proxy.com/" ;
        ZFJCloudRestClient client = ZFJCloudRestClient.restBuilder(zephyrBaseUrl, accessKey, secKey, username).build();
        long issueId = 10000;
        String exeId = "0001515069354754-242ac112-0001";
        boolean isOrderder = true;
        long project = Long.parseLong(projectId);
        String url = zephyrBaseUrl + getTestDetails + issueId + "/0001514974164437-242ac112-0001?projectId=" + project;


        URI uri = new URI(url);
        System.out.println(uri);
        int expirationInSec = 360;
        JwtGenerator jwtGenerator = client.getJwtGenerator();
        String jwt = jwtGenerator.generateJWT("GET", uri, expirationInSec);
        System.out.println(jwt);
        HttpResponse response = null;
        HttpClient restClient = new DefaultHttpClient();

        HttpGet addTestsReq = new HttpGet(uri);
        addTestsReq.addHeader("Content-Type", "application/json");
        addTestsReq.addHeader("Authorization", jwt);
        addTestsReq.addHeader("zapiAccessKey", accessKey);

        try {
            response = restClient.execute(addTestsReq);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode >= 200 && statusCode < 300) {
            String string1 = null;
            HttpEntity entity1 = response.getEntity();
            System.out.println("Sucessfully Executed");
            try {
                string1 = EntityUtils.toString(entity1);
                System.out.println(string1);
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            JSONObject allIssues = new JSONObject(string1);
            //JSONArray IssuesArray = allIssues.getJSONArray("searchObjectList");
            System.out.println(allIssues);
        }
    }*/


    /*public static StringEntity createCycleData(){
        JSONObject createCycleObj = new JSONObject();
        Zapi z = new Zapi();
        createCycleObj.put("name", z.cycleName);
        createCycleObj.put("description", z.cycleDescription);
        createCycleObj.put("startDate", System.currentTimeMillis());
        createCycleObj.put("projectId", Long.parseLong(z.projectId));
        createCycleObj.put("versionId", Long.parseLong(z.versionId));

        StringEntity cycleJSON = null;
        try {
            cycleJSON = new StringEntity(createCycleObj.toString());
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        return cycleJSON;
    }

    public static StringEntity addTestToCycleData(){
        Zapi z = new Zapi();
        String[] issueIds = { "GMAIL-1"};
        JSONObject addTestsObj = new JSONObject();
        addTestsObj.put("issues", issueIds);
        addTestsObj.put("method", "1");
        addTestsObj.put("projectId", Long.parseLong(z.projectId));
        addTestsObj.put("versionId", Long.parseLong(z.versionId));

        StringEntity addTestsJSON = null;
        try {
            addTestsJSON = new StringEntity(addTestsObj.toString());
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        return addTestsJSON;
    }

    public static StringEntity createExecutuion(){
        Zapi z = new Zapi();
        JSONObject status = new JSONObject();
        status.put("id",1);
        int issueid= 10000;
        JSONObject addTestsObj = new JSONObject();
        addTestsObj.put("status",status);
        addTestsObj.put("id","0001515069354754-242ac112-0001");
        addTestsObj.put("projectId", Integer.parseInt(z.projectId));
        addTestsObj.put("issueId",issueid);
        addTestsObj.put("cycleId", z.cycleId);
        addTestsObj.put("versionId", Integer.parseInt(z.versionId));
        addTestsObj.put("assigneeType", "currentUser");
        addTestsObj.put("assignee", "");

        StringEntity addTestsJSON = null;
        try {
            addTestsJSON = new StringEntity(addTestsObj.toString());
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        return addTestsJSON;
    }*/

    /*public List<String> getStepResultData() throws URISyntaxException, IOException {
        List<String> status = new ArrayList<>();

        ZFJCloudRestClient client = ZFJCloudRestClient.restBuilder(zephyrBaseUrl, accessKey, secKey, username).build();
        long issueId = 10000;
        String exeId = "0001515069354754-242ac112-0001";

        String url = zephyrBaseUrl + getStepResult + "executionId=" + exeId + "&issueId=" + issueId;


        URI uri = new URI(url);
        System.out.println(uri);
        int expirationInSec = 360;
        JwtGenerator jwtGenerator = client.getJwtGenerator();
        String jwt = jwtGenerator.generateJWT("GET", uri, expirationInSec);
        System.out.println(jwt);
        HttpResponse response = null;
        HttpClient restClient = new DefaultHttpClient();

        HttpGet addTestsReq = new HttpGet(uri);
        addTestsReq.addHeader("Authorization", jwt);
        addTestsReq.addHeader("zapiAccessKey", accessKey);

        try {
            response = restClient.execute(addTestsReq);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode >= 200 && statusCode < 300) {
            String string1 = null;
            String executionId = null;
            HttpEntity entity1 = response.getEntity();
            System.out.println("Sucessfully Executed");
            string1 = EntityUtils.toString(entity1);
            System.out.println(string1);
            JSONObject allIssues = new JSONObject(string1);
            JSONArray issuesArray = allIssues.getJSONArray("stepResults");

            for (int i = 0; i < issuesArray.length(); i++) {
                JSONObject objects = issuesArray.getJSONObject(i);
                JSONObject data = (JSONObject) objects.get("status");
                String dataAsString = (String)data.get("name");
                status.add(dataAsString);
            }
        }
        return status;
    }*/

    /* public void getExecution() throws URISyntaxException, JSONException, IOException {
        Map<String, String> executionIds = new HashMap<String, String>();
        ZFJCloudRestClient client = ZFJCloudRestClient.restBuilder(zephyrBaseUrl, accessKey, secKey, username).build();
        String exeId = "0001515069354754-242ac112-0001";
        //String url = zephyrBaseUrl + getExecutionDetails +exeId;
        Long version = Long.parseLong(versionId);
        Long projectID = Long.parseLong(projectId);
        String url = zephyrBaseUrl + getCycleInformation +cycleId + "?versionId="+version + "&projectId=" + projectID;

        URI uri = new URI(url);
        System.out.println(uri);
        int expirationInSec = 360;
        JwtGenerator jwtGenerator = client.getJwtGenerator();
        String jwt = jwtGenerator.generateJWT("GET", uri, expirationInSec);

        HttpResponse response = null;
        HttpClient restClient = new DefaultHttpClient();

        System.out.println(jwt);
        HttpGet addTestsReq = new HttpGet(uri);
        addTestsReq.addHeader("Content-Type", "text/plain");
        addTestsReq.addHeader("Authorization", jwt);
        addTestsReq.addHeader("zapiAccessKey", accessKey);
       // addTestsReq.setEntity(executeTestsJSON);

        try {
            response = restClient.execute(addTestsReq);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode >= 200 && statusCode < 300) {
            String string1 = null;
            HttpEntity entity1 = response.getEntity();
            System.out.println("Sucessfully Executed");
            string1 = EntityUtils.toString(entity1);
            System.out.println(string1);
        } else {
            try {
                throw new ClientProtocolException("Unexpected response status: " + statusCode);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            }
        }
    }*/

    public String getToken(URI uri){
        new Zapi();
        int expirationInSec = 360;
        ZFJCloudRestClient client = ZFJCloudRestClient.restBuilder(zephyrBaseUrl, accessKey, secKey, username).build();
        JwtGenerator jwtGenerator = client.getJwtGenerator();
        String jwt = jwtGenerator.generateJWT("GET", uri, expirationInSec);
        return jwt;
    }

    public String postToken(URI uri){
        new Zapi();
        int expirationInSec = 360;
        ZFJCloudRestClient client = ZFJCloudRestClient.restBuilder(zephyrBaseUrl, accessKey, secKey, username).build();
        JwtGenerator jwtGenerator = client.getJwtGenerator();
        String jwt = jwtGenerator.generateJWT("POST", uri, expirationInSec);
        return jwt;
    }

    public HttpResponse getResponse(String url){
        HttpResponse response = null;
        new Zapi();
        int expirationInSec = 360;
        try{
            ZFJCloudRestClient client = ZFJCloudRestClient.restBuilder(zephyrBaseUrl, accessKey, secKey, username).build();
            JwtGenerator jwtGenerator = client.getJwtGenerator();
            URI uri = new URI(url);
            String jwt = jwtGenerator.generateJWT("GET", uri, expirationInSec);
            HttpClient restClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(uri);
            httpGet.setHeader("Authorization", jwt);
            httpGet.setHeader("zapiAccessKey", accessKey);
            response = restClient.execute(httpGet);
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }

    public HttpResponse getResponseInContentType(String url, String contentType){
        HttpResponse response = null;
        new Zapi();
        int expirationInSec = 360;
        try{
            ZFJCloudRestClient client = ZFJCloudRestClient.restBuilder(zephyrBaseUrl, accessKey, secKey, username).build();
            JwtGenerator jwtGenerator = client.getJwtGenerator();
            URI uri = new URI(url);
            String jwt = jwtGenerator.generateJWT("GET", uri, expirationInSec);
            HttpClient restClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(uri);
            httpGet.addHeader("Content-Type", contentType);
            httpGet.setHeader("Authorization", jwt);
            httpGet.setHeader("zapiAccessKey", accessKey);
            response = restClient.execute(httpGet);
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }

    public HttpResponse putResponse(String url, StringEntity entity, String contentType){
        HttpResponse response = null;
        new Zapi();
        int expirationInSec = 360;
        try{
            ZFJCloudRestClient client = ZFJCloudRestClient.restBuilder(zephyrBaseUrl, accessKey, secKey, username).build();
            JwtGenerator jwtGenerator = client.getJwtGenerator();
            URI uri = new URI(url);
            String jwt = jwtGenerator.generateJWT("PUT", uri, expirationInSec);
            HttpClient restClient = new DefaultHttpClient();
            HttpPut httpPut = new HttpPut(uri);
            httpPut.addHeader("Content-Type", contentType);
            //httpPut.addHeader("Content-Type", "application/json");
            httpPut.setHeader("Authorization", jwt);
            httpPut.setHeader("zapiAccessKey", accessKey);
            httpPut.setEntity(entity);
            response = restClient.execute(httpPut);
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }
}
