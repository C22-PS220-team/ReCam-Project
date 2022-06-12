
![logogram](https://user-images.githubusercontent.com/66616896/173228805-f9aac46c-4e77-4545-884c-cb7fc52c8849.png)


# ReCam - Easy Handcraft Ideas from Camera

ReCam is an app that we develop to help people doing 3R (Reduce, Reuse, and Recycle). 
With using combination of machine learning and cloud computing, the app will provide 
handcraft ideas from users camera. So, there is no more reason for us to don't know what to 
do with our garbage.

## Our Survey 

![image](https://user-images.githubusercontent.com/99315609/168441941-168e4f5e-4528-45fe-9155-20bef1b9761b.png)


![image](https://user-images.githubusercontent.com/99315609/168442021-666fa1ee-6df9-4bcd-95a4-eab62ac5decb.png)

[Survei Link](https://docs.google.com/spreadsheets/d/1UO7aOhctn6PdEA-bnhfbERMvbi46cLSGA2PedxdHd7g/edit?usp=sharing)

## Features

- Light/dark mode toggle
- Multi language
- Google Authentication
- Search using image
- Article (News API)

## Screen Shot
![ss for readme](https://user-images.githubusercontent.com/66616896/173229129-4d0c4fdf-31c9-4f86-a928-fac52d0a1035.png)


## API Reference
Base URL = https://apikunodejs.herokuapp.com
 
#### Get items
Endpoint {item} : 
- plastik%0D
- cardboard%0D
- metal%0D
- kertas%0D
- kaca%0D

```http
  GET /{item}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `item` | `string` | **Required**. Item supported  |

#### Response Item

| item | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `judul`      | `string` | Handcraft name |
|`image`    |`string`|Image url|
|`diskripsi`|`string`|Handcraft short description|
|`header1`|`string`|Header for tools and materials|
|`isih1`|`string`|Bullet list of tools and materials|
|`header2`|`string`|Header for procedure|
|`isih1`|`string`|Bullet list of procedure|


## Authors

- [@Rozin Naufal Thoriq](https://github.com/rozinnaufal)
- [@Muhammad Ramdhan](https://github.com/Dhanfinix)
- [@Sindhi Vinata](https://github.com/SindhiVinata)
- [@Ridha Nafila](https://github.com/ridhanafila)
- [@Ruth Pandanggo](https://github.com/rozinnaufal)
- [@Achsan](https://github.com/rozinnaufal)
