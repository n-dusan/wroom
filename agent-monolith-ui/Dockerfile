# Stage 1
FROM node:alpine3.11 as node
WORKDIR /usr/src/app
COPY package*.json ./
RUN npm install
RUN ["npm", "install", "is-promise@2.1.0"]
COPY . .
RUN npm run build --prod

# Stage 2
FROM nginx:1.17.1-alpine
COPY ./nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=node /usr/src/app/dist/agent-ui /usr/share/nginx/html
EXPOSE 80
