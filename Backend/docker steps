./gradlew build
docker rmi -f authserver
docker build -t authserver .

docker tag gcr.io/gocarshare/authserver
docker push gcr.io/gocarshare/authserver

gcloud beta run deploy authserver --image gcr.io/gocarshare/authserver --platform managed --allow-unauthenticated  --region=us-east1

