import React, { useEffect, useState } from 'react'
import CssBaseline from '@mui/material/CssBaseline';
import Box from '@mui/material/Box';
import Container from '@mui/material/Container';
import ImageGallery from './ImageGallery';
import Button from '@mui/material/Button';

import { getImage } from '../service/FetchImagesService';

const ImageUploader = () => {

    const [data,setData] = useState()


useEffect(()=>{
  const fetchData = async () => { 
    try{
      const image = await  getImage("BASE64",1,"50b27e68-75fc-4740-b142-4c0a47ea4560_pexels-shvetsa-5711901.jpg")
      const image2 = await getImage("BYTES",1,"50b27e68-75fc-4740-b142-4c0a47ea4560_pexels-shvetsa-5711901.jpg")
      setData([image,image2]);
    } catch(ex) {
      console.log(ex)
    }
  }

  fetchData();


},[])



if(!data){return <div>loading...</div>
}

console.log(data)

  return (
     <React.Fragment>
      <CssBaseline />
      <Container fixed>
        <Box sx={{ bgcolor: '#cfe8fc', height: '100vh'}} >

            <Button variant='contained'>Upload Image</Button>
            <img style={{width:"399px"}} src={`data:image/jpeg;base64,${data[0]}`} alt="From Base64 String" />
            <img style={{width:"399px"}} src={data[1]} alt="From bytes[]" />
            <ImageGallery itemData={itemData} />
            </Box>
      </Container>
    </React.Fragment>
  )
}

export default ImageUploader

const itemData = [
    {
      img: 'https://images.unsplash.com/photo-1551963831-b3b1ca40c98e',
      title: 'Breakfast',
    },
    {
      img: 'https://images.unsplash.com/photo-1551782450-a2132b4ba21d',
      title: 'Burger',
    },
    {
      img: 'https://images.unsplash.com/photo-1522770179533-24471fcdba45',
      title: 'Camera',
    },
    {
      img: 'https://images.unsplash.com/photo-1444418776041-9c7e33cc5a9c',
      title: 'Coffee',
    },
    {
      img: 'https://images.unsplash.com/photo-1533827432537-70133748f5c8',
      title: 'Hats',
    },
    {
      img: 'https://images.unsplash.com/photo-1558642452-9d2a7deb7f62',
      title: 'Honey',
    },
    {
      img: 'https://images.unsplash.com/photo-1516802273409-68526ee1bdd6',
      title: 'Basketball',
    },
    {
      img: 'https://images.unsplash.com/photo-1518756131217-31eb79b20e8f',
      title: 'Fern',
    },
    {
      img: 'https://images.unsplash.com/photo-1597645587822-e99fa5d45d25',
      title: 'Mushrooms',
    },
    {
      img: 'https://images.unsplash.com/photo-1567306301408-9b74779a11af',
      title: 'Tomato basil',
    },
    {
      img: 'https://images.unsplash.com/photo-1471357674240-e1a485acb3e1',
      title: 'Sea star',
    },
    {
      img: 'https://images.unsplash.com/photo-1589118949245-7d38baf380d6',
      title: 'Bike',
    },
  ];