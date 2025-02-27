pipeline {
    agent any

    parameters {
        choice(name: 'BROWSER', choices: ['chrome', 'firefox'], description: 'Select the browser for test execution')
    }

    environment {
        MAVEN_HOME = "/path/to/maven"  // Update this with your actual Maven path
        CHROME_DRIVER_PATH = "C:/Users/sketc/chromedriver.exe"
        GECKO_DRIVER_PATH = "C:/Users/sketc/geckodriver.exe"
    }

    triggers {
        cron('30 22 * * 4') // Runs every Thursday at 10:30 PM
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    echo "Pulling code from GitHub..."
                    checkout([$class: 'GitSCM',
                        branches: [[name: '*/main']],
                        userRemoteConfigs: [[
                            url: 'git@github.com:your-username/your-private-repo.git',
                            credentialsId: 'your-credentials-id'
                        ]]
                    ])
                }
            }
        }

        stage('Setup Browser') {
            steps {
                script {
                    if (params.BROWSER == 'chrome') {
                        echo "Running tests on Chrome..."
                        env.DRIVER_PATH = CHROME_DRIVER_PATH
                    } else {
                        echo "Running tests on Firefox..."
                        env.DRIVER_PATH = GECKO_DRIVER_PATH
                    }
                }
            }
        }

        stage('Build') {
            steps {
                script {
                    echo "Building the project with Maven..."
                    bat "${MAVEN_HOME}/bin/mvn clean compile"
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    echo "Executing tests with Maven..."
                    def testResult = bat(returnStatus: true, script: "${MAVEN_HOME}/bin/mvn test -Dbrowser=${params.BROWSER}")

                    if (testResult != 0) {
                        error "Test execution failed!"
                    }
                }
            }
        }

        stage('Report') {
            steps {
                script {
                    echo "Publishing HTML Test Report..."
                    publishHTML(target: [
                        reportDir: 'target/surefire-reports',
                        reportFiles: 'index.html',
                        reportName: 'Test Report'
                    ])
                }
            }
        }
    }

    post {
        always {
            echo "Pipeline execution completed!"
        }
        failure {
            echo "Pipeline execution failed!"
        }
    }
}
