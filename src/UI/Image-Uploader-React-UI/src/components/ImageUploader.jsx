import React, { useEffect, useState } from "react";
import CssBaseline from "@mui/material/CssBaseline";
import Box from "@mui/material/Box";
import Container from "@mui/material/Container";
import ImageGallery from "./ImageGallery";
import Button from "@mui/material/Button";
import LinearProgress from "@mui/material/LinearProgress";

import {
  getImage,
  uploadFiles,
  getAllImagesFromGroup,
} from "../service/FetchImagesService";
import NavBar from "./NavBar";

const ImageUploader = () => {
  const [imagesToUpload, setImagesToUpload] = useState([]);
  const [uploadedImagesData, setUploadedImagesData] = useState([]);
  const [errors,setErrors] = useState([])
  const [images, setImages] = useState([]);
  const [groups, setGroups] = useState([])

  // useEffect(() => {
  //   const fetchData = async () => {
  //     if (uploadedImagesData) {
  //       try {
  //         //jak pobieram jako base64 to wyswietlam tak:  src={`data:image/jpeg;base64,${item.img}`}
  //         //jak pobieram jako link to wyswietlam link:  src={item.img}

  //         //const image = await  getImage("BASE64",uploadedImagesData.location,uploadedImagesData.fileName)
  //         // const image2 = await getImage("BYTES",1,"50b27e68-75fc-4740-b142-4c0a47ea4560_pexels-shvetsa-5711901.jpg")

  //         const images = await getAllImagesFromGroup(
  //           uploadedImagesData.location
  //         );

  //         const formattedImages = images.map(image=>{
  //             return
  //         })

  //         setImages([

  //           {
  //             img: image,
  //             title: uploadedImagesData.fileName,
  //           },
  //         ]);
  //         setUploadedImagesData(null);
  //       } catch (ex) {
  //         console.log(ex);
  //       }
  //     }
  //   };
  //   fetchData();
  // }, [uploadedImagesData]);

  const handleAddFile = (event) => {
    const files = event.target.files;
    setImagesToUpload([...imagesToUpload, ...files]);
  };



  const handleFileUpload = async (galleryName) => {
    if (imagesToUpload.length > 0) {
      try {
        const response = await uploadFiles(imagesToUpload, galleryName);
        setImagesToUpload([]);
        console.log(response)
        setUploadedImagesData([
          ...uploadedImagesData,
          ...response.uploadedImages,
        ]);
        if(response.unsavedImages){
          setErrors([...response.unsavedImages])
        }
      } catch (error) {
        console.error("Error uploading file:", error);
      }
    } else {
      console.error("No images to upload");
    }
  };

  return (
    <React.Fragment>
      <CssBaseline />
      <Container fixed>
        <NavBar
          handleAddFile={handleAddFile}
          handleFileUpload={handleFileUpload}
          groups={groups}
          setGroups={setGroups}
          canUpload = { imagesToUpload.length>0 }
        />
        <Box sx={{ bgcolor: "#cfe8fc", height: "100vh" }}>
          {imagesToUpload.length > 0 &&
            imagesToUpload.map((image, index) => {
              return (
                <img
                  key={index}
                  style={{ width: "399px" }}
                  src={URL.createObjectURL(image)}
                  alt="tymczasowy"
                />
              );
            })}
          <ImageGallery itemData={uploadedImagesData} />
        </Box>
      </Container>
    </React.Fragment>
  );
};

export default ImageUploader;

{
  /* <Container fixed>
<Box sx={{ bgcolor: '#cfe8fc', height: '100vh'}} >
    <input type='file' onChange={handleAddFile} accept="image/jpeg, image/png, image/gif, image/webp, image/svg+xml" />
    <Button variant='contained' onClick={handleFileUpload}>Upload Image</Button>
    <img style={{width:"399px"}} src={`data:image/jpeg;base64,${data[0]}`} alt="From Base64 String" />
    { images[0]?<img style={{width:"399px"}} src={images[0].img} alt="From bytes[]" /> : <p>Nie ma</p> }
    <img style={{width:"399px"}} src={data[1]} alt="From resource stream" />
    <ImageGallery itemData={images} />
  </Box>
</Container> */
}

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
//{uploadedImagesData.length>0 && <img style={{width:"399px"}} src={`data:image/jpeg;base64,${images[0]}`} alt="From Base64 String" />}
