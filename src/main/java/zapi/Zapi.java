package zapi;

import com.relevantcodes.extentreports.LogStatus;
import extendreport.ExtentTestManager;
import extendreport.Listner.TestListener;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

/**
 * @author Jainik Bakaraniya 12/29/2017
 * this class is used to call zapi rest api for execute the test step
 * and test case
 */
public class Zapi {

    public static final String JIRA_PROPERTIES_PATH = System.getProperty("user.dir") + "\\src\\main\\resources\\JiraConfig.properties";
    public static String zephyrBaseUrl;
    public static String accessKey;
    public static String secKey;
    public static String username;
    public static String projectId;
    public static String versionId;
    public static String cycleName;
    public static String cycleDescription;
    public static String cycleId;
    public static String executionId;
    public static ZapiRestAPI api = new ZapiRestAPI();

    public Zapi() {
        Properties jiraProperties = new ReadProperties().loadProperties(JIRA_PROPERTIES_PATH);
        zephyrBaseUrl = jiraProperties.getProperty("zephyrBaseUrl");
        accessKey = jiraProperties.getProperty("accessKey");
        secKey = jiraProperties.getProperty("secKey");
        username = jiraProperties.getProperty("username");
        projectId = jiraProperties.getProperty("projectId");
        versionId = jiraProperties.getProperty("versionId");
        cycleName = jiraProperties.getProperty("cycleName");
        cycleDescription = jiraProperties.getProperty("cycleDescription");
        cycleId = jiraProperties.getProperty("cycleId");
        executionId = jiraProperties.getProperty("executionId");
    }

    /**
     * ZAPI URL for operations
     */

    public static String getExecution = "/public/rest/api/1.0/executions/search/cycle/";
    public static String getExecutionDetails = "/public/rest/api/1.0/executions/search?executionId=";
    public static String updaterExecution = "/public/rest/api/1.0/execution/";
    public static String getStepResult = "/public/rest/api/1.0/stepresult/search?";
    public static String updateStepResult = "/public/rest/api/1.0/stepresult/";
    public static String getCycleInformation = "/public/rest/api/1.0/executions/search/cycle/";
    public static String getTestStepDetails = "/public/rest/api/1.0/teststep/";

    /**
     * @param uriStr
     * @return issue id and execution id
     * @throws URISyntaxException
     * @throws JSONException
     */
    private static Map<String, String> getExecutionsByCycleId(String uriStr) throws URISyntaxException, JSONException {
        Map<String, String> executionIds = new HashMap<String, String>();
        HttpResponse response = null;
        response = api.getResponse(uriStr);

        if (response.getStatusLine().getStatusCode() >= 200 && response.getStatusLine().getStatusCode() < 300) {
            HttpEntity entity1 = response.getEntity();
            String string1 = null;
            try {
                string1 = EntityUtils.toString(entity1);
                JSONObject allIssues = new JSONObject(string1);
                JSONArray IssuesArray = allIssues.getJSONArray("searchObjectList");
                if (IssuesArray.length() == 0) {
                    return executionIds;
                }
                for (int j = 0; j <= IssuesArray.length() - 1; j++) {
                    JSONObject jobj = IssuesArray.getJSONObject(j);
                    JSONObject jobj2 = jobj.getJSONObject("execution");
                    String executionId = jobj2.getString("id");
                    long IssueId = jobj2.getLong("issueId");
                    executionIds.put(executionId, String.valueOf(IssueId));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return executionIds;
    }

    public void getAndExecuteCycle() throws Exception {
        Map<String, String> executionIds = new HashMap<String, String>();
        String url = zephyrBaseUrl + getExecution + cycleId + "?projectId=" + projectId + "&versionId=" + versionId;
        executionIds = getExecutionsByCycleId(url);

        JSONObject statusObj = new JSONObject();
        LogStatus s = ExtentTestManager.getTest().getRunStatus();
        if (s.toString().equals("pass")) {
            statusObj.put("id", "1");
        } else {
            statusObj.put("id", "2");
        }
        JSONObject executeTestsObj = new JSONObject();
        executeTestsObj.put("status", statusObj);
        executeTestsObj.put("cycleId", cycleId);
        executeTestsObj.put("projectId", projectId);
        executeTestsObj.put("versionId", versionId);
        executeTestsObj.put("comment", "Executed by Jainik Bakaraniya");

        for (String key : executionIds.keySet()) {
            if (executionIds.get(key).equals(TestListener.getID())) {
                final String updateExecutionUri = zephyrBaseUrl + updaterExecution + key;
                executeTestsObj.put("issueId", TestListener.getID());
                StringEntity executeTestsJSON = null;
                try {
                    executeTestsJSON = new StringEntity(executeTestsObj.toString());
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                updateExecutions(updateExecutionUri, executeTestsJSON);
            }
        }
    }

    public static String updateExecutions(String uriStr, StringEntity executionJSON) throws Exception {
        HttpResponse response = api.putResponse(uriStr, executionJSON,"application/json");
        String executionStatus = "No Test Executed";
        HttpEntity entity = response.getEntity();
        if (response.getStatusLine().getStatusCode() >= 200 && response.getStatusLine().getStatusCode() < 300) {
            String string = null;
            string = EntityUtils.toString(entity);
            JSONObject executionResponseObj = new JSONObject(string);
            JSONObject descriptionResponseObj = executionResponseObj.getJSONObject("execution");
            JSONObject statusResponseObj = descriptionResponseObj.getJSONObject("status");
            executionStatus = statusResponseObj.getString("description");
        } else {
            String string = null;
            string = EntityUtils.toString(entity);
            JSONObject executionResponseObj = new JSONObject(string);
            cycleId = executionResponseObj.getString("clientMessage");
            throw new ClientProtocolException("Unexpected response status: " + response.getStatusLine().getStatusCode());
        }
        return executionStatus;
    }

    public void getAndUpdateStepResult() throws Exception {
        Map<String, String> executionIds = new HashMap<String, String>();
        String url = zephyrBaseUrl + getExecution + cycleId + "?projectId=" + projectId + "&versionId=" + versionId;
        executionIds = getExecutionsByCycleId(url);
        long issueId = 0;
        String exeId = null;
        String stepResultId = null;
        for (String key : executionIds.keySet()) {
            if (TestListener.getID().equalsIgnoreCase(executionIds.get(key))) {
                issueId = Long.parseLong(TestListener.getID());
                exeId = key;
            }
        }
        HttpResponse response = api.getResponse(zephyrBaseUrl + getStepResult + "executionId=" + exeId + "&issueId=" + issueId+"&isOrdered=true");

        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode >= 200 && statusCode < 300) {
            String string1 = null;
            String executionId = null;
            HttpEntity entity1 = response.getEntity();
            System.out.println("Sucessfully Executed");
            string1 = EntityUtils.toString(entity1);
            JSONObject allIssues = new JSONObject(string1);
            JSONArray issuesArray = allIssues.getJSONArray("stepResults");
            for (int i = 0; i < issuesArray.length(); i++) {
                JSONObject objects = issuesArray.getJSONObject(i);
                stepResultId = (String) objects.get("id");
                final String getExecutionUri = zephyrBaseUrl + getTestStepDetails + issueId + "/" + (String) objects.get("stepId") + "?projectId=" + Long.parseLong(projectId);
                getExecution(getExecutionUri, stepResultId, exeId, issueId);
            }
        }
    }

    public String getExecution(String uriStr, String stepResultId, String executionId, long issueId) throws Exception {
        HttpResponse response = api.getResponseInContentType(uriStr, "application/json");
        int statusCode = response.getStatusLine().getStatusCode();
        String data = null;
        if (statusCode >= 200 && statusCode < 300) {
            String string1 = null;
            HttpEntity entity1 = response.getEntity();
            string1 = EntityUtils.toString(entity1);
            JSONObject allSteps = new JSONObject(string1);
            String result = (String) allSteps.get("result");
            String id = (String) allSteps.get("id");

            String status = new TestListener().TestData(result);
            data = id+" "+status;
            JSONObject statusObj = new JSONObject();
            if (status != null){
                if(status.equalsIgnoreCase("FAIL")){
                    statusObj.put("id", "2");
                }else if (status.equalsIgnoreCase("PASS")){
                    statusObj.put("id", "1");
                }
            }else {
                statusObj.put("id", "2");
            }
            JSONObject executeTestsObj = new JSONObject();
            executeTestsObj.put("status", statusObj);
            executeTestsObj.put("issueId", issueId);
            executeTestsObj.put("stepId",id);
            executeTestsObj.put("executionId", executionId);

            final String updateExecutionUri = zephyrBaseUrl + updateStepResult + stepResultId;
            StringEntity executeTestsJSON = null;
            try {
                executeTestsJSON = new StringEntity(executeTestsObj.toString());
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
            updateStepResult(updateExecutionUri, executeTestsJSON);
        } else {
            throw new ClientProtocolException("Unexpected response status: " + statusCode);
        }
        return data;
    }

    public static String updateStepResult(String uriStr, StringEntity executionJSON) throws Exception {
        HttpResponse response = api.putResponse(uriStr, executionJSON,"application/json");
        int statusCode = response.getStatusLine().getStatusCode();
        String executionStatus = "No Test Executed";
        HttpEntity entity = response.getEntity();
        if (statusCode >= 200 && statusCode < 300) {
            System.out.println("Update sucessfully");
        } else {
            throw new Exception("Unexpected response status: " + statusCode);
        }
        return executionStatus;
    }
}
