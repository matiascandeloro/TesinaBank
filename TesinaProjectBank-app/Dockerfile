FROM node

WORKDIR /home/app

COPY  . .

EXPOSE 4200:4200

RUN npm install

RUN npm run build 

CMD ["npm","run","start"]   