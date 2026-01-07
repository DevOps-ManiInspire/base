def utils

pipeline {
    agent any

    environment { 
        AWS_REGION = "us-east-1" 
        STACK_NAME = "${params.StackName}"
        TEMPLATE_FILE = "${params.TemplateFile}"
        PATH = "${env.HOME}/bin:${env.PATH}"
    }
    

    stages {
         stage('Init') {
            steps {
                script {
                    env.STACK_NAME    = params.StackName
                    env.TEMPLATE_FILE = params.TemplateFile
                    utils = load 'cft_utils.groovy'
                }
            }
        }
        stage('checkout') {
            steps {
               checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/DevOps-ManiInspire/base.git']])
            }
        }
        stage('Lint & Validate') { 
            steps { 
                script {
                utils = load 'cft_utils.groovy'
                utils.validateTemplate("my-prod-stack","${WORKSPACE}/template.yml")
                utils.createChangeSet("my-prod-stack","${WORKSPACE}/template.yml")
                utils.updateStack("my-prod-stack","${WORKSPACE}/template.yml")
                }
            } 
        }
    }
}
