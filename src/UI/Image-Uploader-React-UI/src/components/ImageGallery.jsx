import * as React from 'react';
import ImageList from '@mui/material/ImageList';
import ImageListItem from '@mui/material/ImageListItem';

export default function ImageGallery({itemData}) {
  return (
    <ImageList sx={{ width: 500 }} cols={3} rowHeight={164}>
      {itemData.map((item,index) => (
        <ImageListItem key={index}>
          <img
            // srcSet={`${item.img}?w=164&h=164&fit=crop&auto=format&dpr=2 2x`}
            src={URL.createObjectURL(item.img)}
            alt={item.title}
            loading="lazy"
          />
        </ImageListItem>
      ))}
    </ImageList>
  );
}

