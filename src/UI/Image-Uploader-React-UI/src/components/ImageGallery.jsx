import ImageList from '@mui/material/ImageList';
import ImageListItem from '@mui/material/ImageListItem';

export default function ImageGallery({itemData}) {

if(!itemData){
  console.log(itemData)
  return <div>Nothing to show</div>
}

  return (
    <ImageList sx={{ width: 500 }} cols={3} rowHeight={164}>
      {itemData.map((item,index) => (
        <ImageListItem key={index}>
          <img
            // srcSet={`${item.img}?w=164&h=164&fit=crop&auto=format&dpr=2 2x`}
            src={item.imageUrl}
            alt={item.filename}
            loading="lazy"
          />
        </ImageListItem>
      ))}
    </ImageList>
  );
}

