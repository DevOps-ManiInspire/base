def utils

pipeline {
    agent any

    environment { 
        AWS_REGION = "us-east-1" 
        STACK_NAME = "${params.StackName}"
        TEMPLATE_FILE = "${params.TemplateFile}"
        CREATE_CHANGESET = "${params.SkipChangeSet}"
        PATH = "${env.HOME}/bin:${env.PATH}"
    }
    stages {
        stage('checkout') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/main']], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'RelativeTargetDirectory']], submoduleCfg: [], userRemoteConfigs: [[url: 'https://github.com/DevOps-ManiInspire/base/']]])
            }
        }
         stage('Init') {
            steps {
                script {
                    env.STACK_NAME    = params.StackName
                    env.TEMPLATE_FILE = params.TemplateFile
                    utils = load 'cft_utils.groovy'
                }
            }
        }
        stage('Lint & Validate') { 
            steps { 
                script {
                    utils.validateTemplate(env.STACK_NAME, "${env.WORKSPACE}/${env.TEMPLATE_FILE}")
                }
            } 
        }
         stage('CreateChangeSet') { 
            when {
                expression {
                    return env.CREATE_CHANGESET == 'No';
                   }
               }
            steps { 
                script {
                    utils.createChangeSet(${env.STACK_NAME},"${env.BUILD_NUMBER}","${env.WORKSPACE}/${env.TEMPLATE_FILE}")
                def proceed = input(
                        message: 'ChangeSet created. Do you want to proceed?',
                        ok: 'Continue',
                        parameters: [
                            choice(
                                name: 'PROCEED',
                                choices: ['No', 'Yes'],
                                description: 'Select Yes to proceed'
                            )
                        ]
                    )

                    if (proceed == 'Yes') {
                        echo "ChangeSet approved. Proceeding to Deployment"
                    } else {
                        error "Changeset aborted by user."
                    }
                env.PROCEED_DEPLOY = (proceed == 'Yes') ? 'Yes' : 'No'

                }
            } 
        }
            stage('DeployStack') {
                when {
                  expression {
                        env.PROCEED_DEPLOY == 'Yes' || env.CREATE_CHANGESET == 'No'
                    }
                }
                steps {
                    script {
                        utils.updateStack(
                            env.STACK_NAME,
                            "${env.WORKSPACE}/${env.TEMPLATE_FILE}"
                        )
                    }
                }
            }
    }
}
