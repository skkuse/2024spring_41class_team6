import './App.css';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import Main from './pages/Main';
import GitLink from './pages/GitLink';

function App() {
  const BrowserRouter = createBrowserRouter([
    {
      path: '/',
      element: <Main />,
      index: true,
    },
    {
      path: '/gitlink',
      element: <GitLink />,
    },
  ]);

  return (
    <div className="App">
      {/* <header className="App-header">
      </header> */}
      <RouterProvider router={BrowserRouter} />
    </div>
  );
}

export default App;
