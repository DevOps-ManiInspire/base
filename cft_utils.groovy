def validateTemplate(stackName, templateFile, region = "us-east-1") {
    sh """#!/usr/bin/env bash
    set -e
    aws --region ${region} cloudformation validate-template \
        --template-body file:///${templateFile}
    """
}

return this
