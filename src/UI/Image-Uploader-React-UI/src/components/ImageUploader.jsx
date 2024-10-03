import React, { useEffect, useState } from 'react'
import CssBaseline from '@mui/material/CssBaseline';
import Box from '@mui/material/Box';
import Container from '@mui/material/Container';
import ImageGallery from './ImageGallery';
import Button from '@mui/material/Button';
import LinearProgress from '@mui/material/LinearProgress';

import { getImage, uploadFile } from '../service/FetchImagesService';

const ImageUploader = () => {

    const [imagesToUpload,setImagesToUpload] = useState([])
    const [uploadedImagesData, setUploadedImagesData] = useState(null)
    const [images,setImages] = useState([])


useEffect(()=>{
  const fetchData = async () => { 
    if(uploadedImagesData){
    try{
     //jak pobieram jako base64 to wyswietlam tak:  src={`data:image/jpeg;base64,${item.img}`}
     //jak pobieram jako link to wyswietlam link:  src={item.img}

       //const image = await  getImage("BASE64",uploadedImagesData.location,uploadedImagesData.fileName)
      // const image2 = await getImage("BYTES",1,"50b27e68-75fc-4740-b142-4c0a47ea4560_pexels-shvetsa-5711901.jpg")

       const image = await getImage("RESOURCE",uploadedImagesData.location,uploadedImagesData.fileName)
     
       setImages([...images, 
        {
          img: image,
          title:uploadedImagesData.fileName
        }
       ]);
       setUploadedImagesData(null)

    } catch(ex) {
      console.log(ex)
    }
  }
}
  fetchData();
},[uploadedImagesData])

const handleAddFile = (event)=>{

  const file = event.target.files[0]
  setImagesToUpload([...imagesToUpload, file

    ])
}

// const handleFileUpload = async () =>{
//   const response = await uploadFile(images[0]);
//   console.log(response)

// }

const handleFileUpload = async () => {
  if (imagesToUpload.length > 0) {
    try {
      const response = await uploadFile(imagesToUpload[0]);
      setImagesToUpload([])
      setUploadedImagesData(response)  
    } catch (error) {
      
      console.error('Error uploading file:', error);
    }
  } else {
    console.error('No images to upload');
  }
};


// if(!data){
//   return    ( 
//     <Box
//       sx={{
//         display: 'flex',
//         flexDirection: 'column',
//         justifyContent: 'center',
//         gap:'20px',
//         alignItems: 'center',
//         height: '100vh',  // Full viewport height
//       }}
//   >
//     <h2>Loading ...</h2>
//       <Box sx={{
//         width: '400px'
//       }}>
//        <LinearProgress color="success" />
       
//       </Box>
        
//     </Box>
//     )
// }

  return (
     <React.Fragment>
      <CssBaseline />
      <Container fixed>
        <Box sx={{ bgcolor: '#cfe8fc', height: '100vh'}} >
            <input type='file' onChange={handleAddFile} accept="image/jpeg, image/png, image/gif, image/webp, image/svg+xml" />
            <Button variant='contained' onClick={handleFileUpload}>Upload Image</Button>
            {/* {uploadedImagesData.length>0 && <img style={{width:"399px"}} src={`data:image/jpeg;base64,${images[0]}`} alt="From Base64 String" />} */}
            {imagesToUpload.length>0 && <img style={{width:"399px"}} src={URL.createObjectURL(imagesToUpload[0])} alt="tymczasowy" />}
            <ImageGallery itemData={images} />
          </Box>
      </Container>
    </React.Fragment>
  )
}

export default ImageUploader




{/* <Container fixed>
<Box sx={{ bgcolor: '#cfe8fc', height: '100vh'}} >
    <input type='file' onChange={handleAddFile} accept="image/jpeg, image/png, image/gif, image/webp, image/svg+xml" />
    <Button variant='contained' onClick={handleFileUpload}>Upload Image</Button>
    <img style={{width:"399px"}} src={`data:image/jpeg;base64,${data[0]}`} alt="From Base64 String" />
    { images[0]?<img style={{width:"399px"}} src={images[0].img} alt="From bytes[]" /> : <p>Nie ma</p> }
    <img style={{width:"399px"}} src={data[1]} alt="From resource stream" />
    <ImageGallery itemData={images} />
  </Box>
</Container> */}

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