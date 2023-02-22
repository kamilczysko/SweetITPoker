/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
  ],
  theme: {
    colors: {
      'bg1': '#FFDEE9',
      'bg2': '#B5FFFC',
      'bg3': '#C850C0'
    },
    gridTemplateColumns: {
      inputGrid: '1fr 3fr'
    },
    fontFamily: {
      main: 'Lobster',
      secondary: 'Roboto'
    },
    extend: {
    },
  },
  plugins: [],
}