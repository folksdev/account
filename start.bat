cd account-api
docker compose up -d
cd ../

cd account-fe
npm install
npm start

echo "Service is ready"