pipeline {
    agent any

    stages {
        stage ('checkout repos') {
            steps {
                    'checkout repos': {
                            script {
                                    checkout([$class: 'GitSCM', branches: [[name: '*/main']], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: 'datascience']], submoduleCfg: [], userRemoteConfigs: [[url: 'https://github.com/DevOps-ManiInspire/base.git']]])
                            }
                    }
            }
        }
        stage('Deploy') {
            steps {
                script {
                    // Load the external Groovy file
                    def utils = load 'cft_utils.groovy'

                    // Call the function inside it
                    utils.update_cf("my-prod-stack")
                }
            }
        }
    }
}
