job("NewsAggregatorPipeline") {
    description = "Pipeline for News Aggregator"

    scm {
        git {
            remote {
                url = ""
                branch = "master"
            }
        }
    }
    steps {
        shell("./gradlew clean build")

        shell("""
            docker build -t your-dockerhub-username/news-aggregator .
            docker push your-dockerhub-username/news-aggregator
        """)

        shell(""" 
            docker stop news-aggregator || true
            docker rm news-aggregator || true
                docker run -d --name news-aggregator -p 8080:8080 your-dockerhub-username/news-aggregator
        """.trimIndent())
    }

    triggers {
        scm("H/5 * * * *") // Poll SCM every 5 minutes
    }
}