# Stage 1
FROM node:alpine3.11 as node
WORKDIR /usr/src/app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build --prod

# Stage 2
FROM nginx:1.17.1-alpine
COPY ./nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=node /usr/src/app/dist/agent-ui /usr/share/nginx/html
EXPOSE 80

##MOJE
#FROM node:alpine3.11 AS builder
#
#LABEL maintainer="nikolic.dusan@uns.ac.rs"
#
#WORKDIR /usr/src/app
#COPY . .
#RUN ["npm", "install"]
#EXPOSE 4200
#CMD [ "npm", "run", "build", "--prod"]

##PREUZETO SA NETA
#FROM nginx:1.17.1-alpine
#COPY --from=builder /usr/src/dist/frontend-agent/ /usr/share/nginx/html
#EXPOSE 80
