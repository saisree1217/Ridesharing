./gradlew clean
./gradlew build
docker rmi -f $(docker images | grep 'scheduleservice')
docker build -t scheduleservice .

docker tag scheduleservice gcr.io/gocarshare/scheduleservice
docker push gcr.io/gocarshare/scheduleservice

gcloud beta run deploy scheduleservice --image gcr.io/gocarshare/scheduleservice --platform managed --allow-unauthenticated  --region=us-east1
