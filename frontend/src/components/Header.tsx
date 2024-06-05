import { ReactComponent as ELogo } from '../icons/airplane_ticket.svg';
import Nav from './Nav';
import styled from 'styled-components';

function Header() {
  return (
    <HeaderContainer>
      <Logo className="header-logo">
        <ELogo fill="var(--primary-100)" />
        <SiteName>ECOder Passport</SiteName>
      </Logo>
      <Nav />
    </HeaderContainer>
  );
}

// style
const HeaderContainer = styled.div`
  display: flex;
  flex-direction: row;
  width: 100%;
  justify-content: space-between;
  align-items: center;
  padding: 16px 48px;
  box-sizing: border-box;
`;
const Logo = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  gap: 16px;
  align-items: center;
`;

const SiteName = styled.div`
  font-weight: 500;
  font-size: 24px;
  font-family: 'Pacifico', cursive;
`;
export default Header;
