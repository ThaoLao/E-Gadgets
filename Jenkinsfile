pipeline {
    agent any

    environment {
        KRE_HOME = 'D:\\Katalon_Studio_Engine_Windows_64-11.1.2' 
    }

    stages {
        stage('Checkout Code') {
            steps {
                // Tự động kéo mã nguồn mới nhất từ Github
                checkout scm
                echo "Pull code tu Github thanh cong!"
            }
        }

        stage('Execute Katalon Tests') {
            steps {
                echo "Bat dau chay kien thu tu dong (headless) voi KRE..."
                bat """
                    "%KRE_HOME%".\\katalonc.exe -noSplash -runMode=console -projectPath="C:\\Users\\HP\\Desktop\\DATN\\Source\\test\\E-Gadgets\\RookBookStore.prj" -retry=0 -testSuitePath="Test Suites/TS_Admin/TS_AllAdmin" -browserType="Chrome (headless)" -apiKey="fdb34b80-ed30-4efc-830a-378361b6cd4b" -sendEmail="laothao2k2@gmail.com"
                """
            }
        }
    }

    post {
        always {
            echo "Katalon da hoan tat viec chay test va tu dong gui mail."
            archiveArtifacts artifacts: 'test/E-Gadgets/Reports/**/*.*', allowEmptyArchive: true
        }
        success {
            echo "Test Pass! Luong CI/CD thanh cong."
        }
        failure {
            echo "Test co loi! Luong CI/CD bi huy. Kiem tra hòm thư email de xem log bao cao do Katalon gui."
        }
    }
}
