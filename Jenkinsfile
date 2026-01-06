pipeline {
    agent any

    environment { 
        AWS_REGION = "us-east-1" 
        STACK_NAME = "my-prod-stack" 
        TEMPLATE_FILE = "template.yml"
        PATH = "${env.HOME}/bin:${env.PATH}"
    }
    

    stages {
        stage('checkout') {
            steps {
               checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/DevOps-ManiInspire/base.git']])
            }
        }
        stage('Lint & Validate') { 
            steps { 
                script {
              def utils = load 'cft_utils.groovy'
              utils.validateTemplate("my-prod-stack","${WORKSPACE}/template.yml")

                utils.updateStack("my-prod-stack","${WORKSPACE}/template.yml")
                }
            } 
        }
    }
}
