package com.uxpin.devops;

import asg.cliche.Command;
import asg.cliche.Param;
import asg.cliche.ShellFactory;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    protected void output(String str) {
        System.out.println(str);
    }

    @Command(name="ec2", description = "Find ec2 instance with given name")
    public String ec2(
            @Param(name = "name", description = "Name of the instance")
                    String name) {

        output("You're looking for " + name);
        AmazonEC2Client client = new AmazonEC2Client();
        DescribeInstancesRequest request = new DescribeInstancesRequest();
        List<String> namesList = new ArrayList<String>();
        namesList.add("*"+name+"*");
        request.withFilters(new Filter("tag-value", namesList));
        DescribeInstancesResult result = client.describeInstances(request);
        return result.toString();
    }

    public static void main(String[] args) throws IOException {
        ShellFactory.createConsoleShell("aws-cli", "Hi!", new Main())
                .commandLoop();
    }
}