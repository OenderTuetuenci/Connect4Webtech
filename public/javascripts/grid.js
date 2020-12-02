//Setting Up Variables for responsive Design
const canvas = document.getElementById("mycanvas");
const ctx = canvas.getContext("2d");
ctx.lineWidth = 2
const width = canvas.width;
const height = canvas.height;

const cellwidth = Math.floor(width/7);
const cellheight = Math.floor(height/6);
const stoneradius = Math.floor(cellheight/2.5);

let widthlines = [0,0,0,0,0,0];
let heightlines = [0,0,0,0,0];
let centerx = []
let centery = []

let x = Math.floor(cellwidth/2)
let y = Math.floor(cellheight/2)
for(let i = 0; i < 7;i++){
    centerx.push(x);
    x+=cellwidth;
}
for(let i = 0; i < 6;i++){
    centery.push(y);
    y+=cellheight;
}

let start = 0;
for(let i = 0; i<widthlines.length;i++){
    widthlines[i]=start+cellwidth;
    start+=cellwidth;
}

start = 0;
for(let i = 0; i<widthlines.length;i++){
    heightlines[i]=start+cellheight;
    start+=cellheight;
}
//Drawing CellBorders
for(let i = 0; i<widthlines.length;i++){
    ctx.beginPath();
    ctx.moveTo(widthlines[i],0);
    ctx.lineTo(widthlines[i],height);
    ctx.stroke();
}
for(let i = 0; i<widthlines.length;i++){
    ctx.beginPath();
    ctx.moveTo(0,heightlines[i]);
    ctx.lineTo(width,heightlines[i]);
    ctx.stroke();
}
function draw(button) {
    $.ajax({
        method: "GET",
        url: "/json/"+button,
        dataType: "json",

        success: function (result) {
            drawCircles(result)
        }
    });
}
function drawCircles(json){
    cells = json.grid.cells;
    for(let i = 0; i < cells.length;i++){
        let val = cells[i].val;
        let row = cells[i].row;
        let col = cells[i].col;
        if(val === 1){
            ctx.fillStyle = "#c82124";
        }else{
            ctx.fillStyle = "#3370d4";
        }
        if(val !== 0){
            ctx.beginPath();
            ctx.arc(centerx[col],centery[row],stoneradius,0,2*Math.PI);
            ctx.fill();
        }
    }
}

