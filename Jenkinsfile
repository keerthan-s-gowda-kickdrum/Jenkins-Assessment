pipeline {
    agent any

    parameters {
        choice(name: 'BROWSER', choices: ['chrome', 'firefox', 'edge'], description: 'Choose the browser for test execution')
    }

    triggers {
        cron('30 22 * * 4') // Poll GitHub every Thursday at 10:30 PM
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    git credentialsId: 'github-jenkins-creds',
                        branch: 'master',
                        url: 'https://github.com/keerthan-s-gowda-kickdrum/Jenkins-Assessment.git'
                }
            }
        }

        stage('Build') {
            steps {
                script {
                    try {
                        bat "mvn clean compile"
                    } catch (Exception e) {
                        error "Build failed. Skipping tests."
                    }
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    def selectedBrowser = params.BROWSER
                    bat "mvn test -Dbrowser=${selectedBrowser}"
                }
            }
        }

        stage('Generating the Report') {
            steps {
                script {
                    bat "mvn surefire-report:report"
                }
            }
        }

        stage('Publish HTML Report') {
            steps {
                publishHTML(target: [
                    reportDir: 'target/site',
                    reportFiles: 'surefire-report.html',
                    reportName: 'Test Report',
                    keepAll: true
                ])
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
        }
    }
}
