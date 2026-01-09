// cft_utils.groovy

def validateTemplate(stackName, templateFile, region = "us-east-1") {
    sh """#!/usr/bin/env bash
    set -e
    aws --region ${region} cloudformation validate-template \
        --template-body file:///${templateFile}
    """
    }

def createChangeSet(stackName, templateFile, region = "us-east-1") {
    sh """#!/usr/bin/env bash
    set -e
    aws --region ${region} cloudformation create-change-set \
        --stack-name ${stackName} \
        --change-set-name ${stackName}-changeset \
        --template-body file:///${templateFile} \
        --capabilities CAPABILITY_IAM CAPABILITY_NAMED_IAM
    """
    }

def updateStack(stackName, templateFile, region = "us-east-1") {
    sh """#!/usr/bin/env bash
    set -e
    aws --region ${region} cloudformation deploy \
        --stack-name ${stackName} \
        --template-file ${templateFile} \
        --no-fail-on-empty-changeset \
        --capabilities CAPABILITY_IAM CAPABILITY_NAMED_IAM
    """
    }

// MUST be at the very end so `load` works
return this
